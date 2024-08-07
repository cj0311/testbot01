package com.tradingview.testbot01.exchange;

import org.apache.commons.codec.binary.Hex;
import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.util.RetryUtil;
import lombok.RequiredArgsConstructor;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.trade.BinanceOrderFlags;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class BinancePlatform implements TradingPlatform {

    private final BinanceExchange exchange;
    private final BinanceTradeService tradeService;
    private String positionMode = "one-way";
    private final RetryUtil retryUtil;

    @Autowired
    public BinancePlatform(Environment env, RetryUtil retryUtil) {

        String apiKey = env.getProperty("BINANCE_API_KEY");
        String secret = env.getProperty("BINANCE_SECRET");
        this.retryUtil = retryUtil;
        ExchangeSpecification spec = new BinanceExchange().getDefaultExchangeSpecification();
        spec.setApiKey(apiKey);
        spec.setSecretKey(secret);
        this.exchange = (BinanceExchange) ExchangeFactory.INSTANCE.createExchange(spec);
        this.tradeService = (BinanceTradeService) exchange.getTradeService();
    }

//    private BinanceExchange exchange;
//
//    public BinancePlatform(@Value("${binance.api.key}") String apiKey,
//                           @Value("${binance.secret.key}") String secretKey) {
//        ExchangeSpecification spec = new BinanceExchange().getDefaultExchangeSpecification();
//        spec.setApiKey(apiKey);
//        spec.setSecretKey(secretKey);
//        this.exchange = (BinanceExchange) ExchangeFactory.INSTANCE.createExchange(spec);
//    }

    @Override
    public void initInfo(TradingViewOrder order) {
        if (order.isFutures()) {
            if (order.isUsdtMarginedFutures()) {
                exchange.getExchangeSpecification().setExchangeSpecificParametersItem("Use_USDM", true);
            } else if (order.isCoinMarginedFutures()) {
                exchange.getExchangeSpecification().setExchangeSpecificParametersItem("Use_COINM", true);
            }
        } else {
            exchange.getExchangeSpecification().setExchangeSpecificParametersItem("Use_Futures", false);
        }
        exchange.applySpecification(exchange.getExchangeSpecification());
    }



    @Override
    public OrderResult marketEntry(TradingViewOrder order) {
        try {
            return retryUtil.retry(() -> {
                setLeverageIfNeeded(order);
                org.knowm.xchange.dto.trade.MarketOrder xchangeOrder = convertToXChangeMarketOrder(order);
                String orderId = exchange.getTradeService().placeMarketOrder(xchangeOrder);
                return new OrderResult(orderId);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute market entry order", e);
        }
    }
    @Override
    public OrderResult marketClose(TradingViewOrder order) {
        try {
            return retryUtil.retry(() -> {
                MarketOrder xchangeOrder = convertToXChangeMarketOrder(order);
    //            xchangeOrder.getExchangeSpecificParameters().put("reduceOnly", true);
    //            xchangeOrder = xchangeOrder.toBuilder().addParameter("reduceOnly", true).build();
                xchangeOrder = new MarketOrder.Builder(xchangeOrder.getType(), xchangeOrder.getInstrument())
                        .originalAmount(xchangeOrder.getOriginalAmount())
                        .flag(BinanceOrderFlags.REDUCE_ONLY)
                        .build();
                String orderId = tradeService.placeMarketOrder(xchangeOrder);
                return new OrderResult(orderId);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public OrderResult marketBuy(TradingViewOrder order) {
        return marketEntry(order);
    }

    @Override
    public OrderResult marketSell(TradingViewOrder order) {
        return marketEntry(order);
    }

    @Override
    public OrderResult createOrder(TradingViewOrder order) {
        throw new UnsupportedOperationException("Create order not supported for Binance");
    }

    @Override
    public double getAmount(TradingViewOrder order) {
        if (order.getAmount() != null) {
            return order.getAmount();
        } else if (order.getPercent() != null) {
            // Implement percent-based amount calculation
            // This might involve fetching account balance or position size
            throw new UnsupportedOperationException("Percent-based amount calculation not implemented");
        }
        throw new IllegalArgumentException("Either amount or percent must be specified");
    }
//
//    @Override
//    public void setLeverage(int leverage, String symbol) {
//        tradeService.changeInitialLeverage(symbol, leverage);
//    }

    @Override
    public String getPositionMode() {
        return this.positionMode;
    }

    @Override
    public void setPositionMode(String mode) {
        this.positionMode = mode;
        // Implement actual position mode change on Binance if needed
    }

    private MarketOrder convertToXChangeMarketOrder(TradingViewOrder order) {
        return new MarketOrder.Builder(getOrderType(order), new CurrencyPair(order.getBase(), order.getQuote()))
                .originalAmount(BigDecimal.valueOf(getAmount(order)))
                .build();
    }

    private Order.OrderType getOrderType(TradingViewOrder order) {
        if (order.isBuy() || (order.isEntry() && order.getSide().equalsIgnoreCase("buy"))) {
            return Order.OrderType.BID;
        } else {
            return Order.OrderType.ASK;
        }
    }

    private void setLeverageIfNeeded(TradingViewOrder order) throws Exception {
        if (order.isFutures() && order.getLeverage() != null) {
            setLeverage(order.getLeverage(), order.getUnifiedSymbol());
        }
    }

    @Override
    public void setLeverage(int leverage, String symbol) throws Exception {
        String url = "https://fapi.binance.com/fapi/v1/leverage";  // Binance Futures API endpoint
        String queryString = String.format("symbol=%s&leverage=%d&timestamp=%d", symbol, leverage, System.currentTimeMillis());
        String signature = createSignature(queryString);  // Binance 서명 생성 메소드 (별도로 구현 필요)

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?" + queryString + "&signature=" + signature))
                .header("X-MBX-APIKEY", exchange.getExchangeSpecification().getApiKey())
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Failed to set leverage: " + response.body());
        }
    }

    private String createSignature(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(exchange.getExchangeSpecification().getSecretKey().getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
    }
}