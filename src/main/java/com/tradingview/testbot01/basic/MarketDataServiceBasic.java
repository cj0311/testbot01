package com.tradingview.testbot01.basic;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MarketDataServiceBasic {

    private final Exchange binanceExchange;

    @Autowired
    public MarketDataServiceBasic(Exchange binanceExchange) {
        this.binanceExchange = binanceExchange;
    }

    public Ticker getTicker(Instrument currencyPair) throws IOException {
        MarketDataService marketDataService = binanceExchange.getMarketDataService();
        return marketDataService.getTicker(currencyPair);
    }
}