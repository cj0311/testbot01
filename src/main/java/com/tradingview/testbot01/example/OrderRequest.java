package com.tradingview.testbot01.example;

import lombok.Data;

@Data
public class OrderRequest {
    private String base;
    private String quote;
    private String side;  // e.g., "bid", "ask", "exit_bid", "exit_ask"
    private String amount;

}