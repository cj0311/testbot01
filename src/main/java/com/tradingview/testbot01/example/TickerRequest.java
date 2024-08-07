package com.tradingview.testbot01.example;

import lombok.Data;

@Data
public class TickerRequest {
    private String base;
    private String quote;
}
