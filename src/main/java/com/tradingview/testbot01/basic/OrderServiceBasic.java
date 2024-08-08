package com.tradingview.testbot01.basic;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class OrderServiceBasic {

    private final Exchange binanceExchange;

    @Autowired
    public OrderServiceBasic(Exchange binanceExchange) {
        this.binanceExchange = binanceExchange;
    }

    public String placeLimitOrder(CurrencyPair currencyPair, OrderType orderType, BigDecimal amount, BigDecimal price) throws IOException {
        TradeService tradeService = binanceExchange.getTradeService();
        LimitOrder limitOrder = new LimitOrder.Builder(orderType, currencyPair)
                .limitPrice(price)
                .originalAmount(amount)
                .build();
        return tradeService.placeLimitOrder(limitOrder);
    }

    public String placeMarketOrder(CurrencyPair currencyPair, OrderType orderType, BigDecimal amount) throws IOException {
        TradeService tradeService = binanceExchange.getTradeService();
        MarketOrder marketOrder = new MarketOrder.Builder(orderType, currencyPair)
                .originalAmount(amount)
                .build();
        return tradeService.placeMarketOrder(marketOrder);
    }
}