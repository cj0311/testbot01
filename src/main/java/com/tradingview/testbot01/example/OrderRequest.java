package com.tradingview.testbot01.example;

import lombok.Data;

@Data
public class OrderRequest {
    private String base;
    private String quote;
    private String side;
    private String amount;

}