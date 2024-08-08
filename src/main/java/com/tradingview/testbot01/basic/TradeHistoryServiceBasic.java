package com.tradingview.testbot01.basic;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TradeHistoryServiceBasic {

    private final Exchange binanceExchange;

    @Autowired
    public TradeHistoryServiceBasic(Exchange binanceExchange) {
        this.binanceExchange = binanceExchange;
    }

    public UserTrades getTradeHistory() throws IOException {
        TradeService tradeService = binanceExchange.getTradeService();
        TradeHistoryParams params = tradeService.createTradeHistoryParams();
        return tradeService.getTradeHistory(params);
    }
}