package com.tradingview.testbot01.repository;


import com.tradingview.testbot01.domain.TradingViewAlert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class AlertRepository {
    private final List<TradingViewAlert> alerts = new CopyOnWriteArrayList<>();

    public void addAlert(TradingViewAlert alert) {
        alerts.add(alert);
    }

    public List<TradingViewAlert> getRecentAlerts(int limit) {
        int size = alerts.size();
        return new ArrayList<>(alerts.subList(Math.max(size - limit, 0), size));
    }
}
