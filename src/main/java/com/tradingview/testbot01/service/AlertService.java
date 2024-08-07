package com.tradingview.testbot01.service;

import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public void processAlert(TradingViewAlert alert) {
        log.info("Received alert: {}", alert);
        alertRepository.save(alert);

        if ("BUY".equals(alert.getAction())) {
            placeBuyOrder(alert);
        } else if ("SELL".equals(alert.getAction())) {
            placeSellOrder(alert);
        }
    }

    private void placeBuyOrder(TradingViewAlert alert) {
        log.info("Placing buy order for {} at price {}", alert.getSymbol(), alert.getPrice());
    }

    private void placeSellOrder(TradingViewAlert alert) {
        log.info("Placing sell order for {} at price {}", alert.getSymbol(), alert.getPrice());
    }

    public List<TradingViewAlert> getRecentAlerts(int limit) {
        return alertRepository.findTop10ByOrderByCreatedAtDesc();
    }
}