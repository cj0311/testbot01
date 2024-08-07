package com.tradingview.testbot01.exchange;


import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.domain.OrderResult;

import java.io.IOException;

public interface TradingPlatform {
    void initInfo(TradingViewOrder order);
    OrderResult marketEntry(TradingViewOrder order);
    OrderResult marketClose(TradingViewOrder order);
    OrderResult marketBuy(TradingViewOrder order);
    OrderResult marketSell(TradingViewOrder order);
    OrderResult createOrder(TradingViewOrder order);
    double getAmount(TradingViewOrder order);
    void setLeverage(int leverage, String symbol) throws Exception;
    String getPositionMode();
    void setPositionMode(String mode);
//    OrderResult createOrder(TradingViewOrder order) throws Exception;
}
