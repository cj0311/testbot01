package com.tradingview.testbot01.service;


import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@Service
public class BinanceService {

    private final Exchange exchange;
    private final AccountService accountService;
    private final TradeService tradeService;

    @Autowired
    public BinanceService(Environment env) {

        String apiKey = env.getProperty("BINANCE_API_KEY");
        String secret = env.getProperty("BINANCE_SECRET");

        if (apiKey == null || secret == null) {
            throw new IllegalStateException("Binance API key and secret must be set in environment variables");
        }
        ExchangeSpecification exSpec = new BinanceExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secret);
        exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
        accountService = exchange.getAccountService();
        tradeService = exchange.getTradeService();
    }

    public AccountInfo getAccountInfo() throws IOException {
        return accountService.getAccountInfo();
    }

    public String placeBuyOrder(String symbol, BigDecimal amount) throws IOException {
        CurrencyPair pair = new CurrencyPair(symbol);
        MarketOrder order = new MarketOrder.Builder(Order.OrderType.BID, pair)
                .originalAmount(amount)
                .build();
        return tradeService.placeMarketOrder(order);
    }

    public String placeSellOrder(String symbol, BigDecimal amount) throws IOException {
        CurrencyPair pair = new CurrencyPair(symbol);
        MarketOrder order = new MarketOrder.Builder(Order.OrderType.ASK, pair)
                .originalAmount(amount)
                .build();
        return tradeService.placeMarketOrder(order);
    }

    // 추가: 지정가 주문 메서드
    public String placeLimitBuyOrder(String symbol, BigDecimal amount, BigDecimal price) throws IOException {
        CurrencyPair pair = new CurrencyPair(symbol);
        LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, pair)
                .originalAmount(amount)
                .limitPrice(price)
                .build();
        return tradeService.placeLimitOrder(order);
    }

    public String placeLimitSellOrder(String symbol, BigDecimal amount, BigDecimal price) throws IOException {
        CurrencyPair pair = new CurrencyPair(symbol);
        LimitOrder order = new LimitOrder.Builder(Order.OrderType.ASK, pair)
                .originalAmount(amount)
                .limitPrice(price)
                .build();
        return tradeService.placeLimitOrder(order);
    }

    public void cancelOrder(String orderId, CurrencyPair pair) throws IOException {
        tradeService.cancelOrder(orderId);
    }
    public Collection<Order> getOrder(String orderId) throws IOException {
        OrderQueryParams params = new OrderQueryParams() {
            private String id;

            @Override
            public String getOrderId() {
                return id;
            }

            @Override
            public void setOrderId(String orderId) {
                this.id = orderId;
            }
        };
        params.setOrderId(orderId);
        return tradeService.getOrder(params);
    }
}