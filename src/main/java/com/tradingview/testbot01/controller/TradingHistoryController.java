package com.tradingview.testbot01.controller;

import com.tradingview.testbot01.domain.TradeHistory;
import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.repository.AlertRepository;
import com.tradingview.testbot01.repository.TradeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TradingHistoryController {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;

    @GetMapping("/trading-history")
    public String getTradingHistory(Model model) {
        List<TradingViewAlert> recentAlerts = alertRepository.findTop10ByOrderByCreatedAtDesc();
        List<TradeHistory> recentTrades = tradeHistoryRepository.findTop50ByOrderByTradeTimeDesc();

        model.addAttribute("alerts", recentAlerts);
        model.addAttribute("trades", recentTrades);

        return "trading-history";
    }
}