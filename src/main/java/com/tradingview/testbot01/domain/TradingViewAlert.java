package com.tradingview.testbot01.domain;


import lombok.Data;

@Data
public class TradingViewAlert {
    private String symbol;
    private String interval;
    private String strategy;
    private String action;
    private double price;
    // Add any other fields that TradingView sends in its webhook
}