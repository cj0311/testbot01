package com.tradingview.testbot01.service;


import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.domain.OrderResult;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logOrderResult(String exchange, OrderResult result, TradingViewOrder order) {
        logger.info("Order executed on {}: Result={}, Order={}", exchange, result, order);
    }

    public void logError(String message, Exception e) {
        logger.error(message, e);
    }
}