package com.tradingview.testbot01.example;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class BinanceService {

    @Autowired
    private Exchange binanceExchange;

    public String placeMarketOrder(String base, String quote, Order.OrderType orderType, BigDecimal amount) throws IOException {
        TradeService tradeService = binanceExchange.getTradeService();
        CurrencyPair pair = new CurrencyPair(base, quote);

        MarketOrder marketOrder = new MarketOrder(orderType, amount, pair);
//        return tradeService.placeMarketOrder(marketOrder).getId();
        return tradeService.placeMarketOrder(marketOrder);
    }
}
