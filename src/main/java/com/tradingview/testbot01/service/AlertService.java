package com.tradingview.testbot01.service;

import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertService {

    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    private final AlertRepository alertRepository;

    public void processAlert(TradingViewAlert alert) {
        // Log the received alert
        logger.info("Received alert: {}", alert);
        alertRepository.addAlert(alert);

        // Here you can add your trading logic
        // For example, you might want to place an order based on the alert
        if ("BUY".equals(alert.getAction())) {
            placeBuyOrder(alert);
        } else if ("SELL".equals(alert.getAction())) {
            placeSellOrder(alert);
        }
    }

    private void placeBuyOrder(TradingViewAlert alert) {
        // Implement your buy order logic here
        logger.info("Placing buy order for {} at price {}", alert.getSymbol(), alert.getPrice());
    }

    private void placeSellOrder(TradingViewAlert alert) {
        // Implement your sell order logic here
        logger.info("Placing sell order for {} at price {}", alert.getSymbol(), alert.getPrice());
    }
    public List<TradingViewAlert> getRecentAlerts(int limit) {
        return alertRepository.getRecentAlerts(limit);

    }
}
