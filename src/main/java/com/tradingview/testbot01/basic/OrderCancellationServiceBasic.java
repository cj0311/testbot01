package com.tradingview.testbot01.basic;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderCancellationServiceBasic {

    private final Exchange binanceExchange;

    @Autowired
    public OrderCancellationServiceBasic(Exchange binanceExchange) {
        this.binanceExchange = binanceExchange;
    }

    public boolean cancelOrder(String orderId) throws IOException {
        TradeService tradeService = binanceExchange.getTradeService();
        return tradeService.cancelOrder(orderId);
    }
}