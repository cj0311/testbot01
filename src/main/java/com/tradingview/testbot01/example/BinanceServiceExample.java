package com.tradingview.testbot01.example;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Slf4j
public class BinanceServiceExample {

    @Autowired
    private Exchange binanceExchange;

    public String placeMarketOrder(String base, String quote, Order.OrderType orderType, BigDecimal amount) throws IOException {
        log.info("base = " + base + ", quote = " + quote + ", orderType = " + orderType + ", amount = " + amount);
        TradeService tradeService = binanceExchange.getTradeService();
        log.info("1");
        Instrument pair = new CurrencyPair(base, quote);

        MarketOrder marketOrder = new MarketOrder(orderType, amount, pair);
        log.info("2");
        String tradeMsg = tradeService.placeMarketOrder(marketOrder);
        log.info(tradeMsg);
//        return tradeService.placeMarketOrder(marketOrder).getId();
        return tradeMsg;
    }


    public BigDecimal getTickerPrice(String base, String quote) throws IOException {

        log.info("base = " + base + ", quote = " + quote);
        MarketDataService marketDataService = binanceExchange.getMarketDataService();

        Instrument pair = new CurrencyPair(base, quote);

        Ticker ticker = marketDataService.getTicker(pair);
        log.info(ticker.toString());
        return ticker.getLast();
    }
}
