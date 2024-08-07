package com.tradingview.testbot01.domain;

import lombok.Data;

@Data
public class TradingViewOrder {
    private String exchange;
    private String base;
    private String quote;
    private String side;
    private Double amount;
    private Double price;
    private String type;
    private Integer leverage;
    private Double percent;
    private String unifiedSymbol;
    // Add these methods
    public boolean isCrypto() {
        return "BINANCE".equalsIgnoreCase(exchange) || "UPBIT".equalsIgnoreCase(exchange);
    }

    public boolean isStock() {
        return "KRX".equalsIgnoreCase(exchange) || "NASDAQ".equalsIgnoreCase(exchange);
    }

    public boolean isFutures() {
        return type != null && type.toLowerCase().contains("futures");
    }

    public boolean isSpot() {
        return !isFutures();
    }

    public boolean isEntry() {
        return side != null && side.toLowerCase().startsWith("entry");
    }

    public boolean isClose() {
        return side != null && side.toLowerCase().startsWith("close");
    }

    public boolean isBuy() {
        return "buy".equalsIgnoreCase(side) || (isEntry() && side.toLowerCase().endsWith("buy"));
    }

    public boolean isSell() {
        return "sell".equalsIgnoreCase(side) || (isEntry() && side.toLowerCase().endsWith("sell"));
    }

    public boolean isUsdtMarginedFutures() {
        return isFutures() && "USDT".equalsIgnoreCase(quote);
    }

    public boolean isCoinMarginedFutures() {
        return isFutures() && !"USDT".equalsIgnoreCase(quote);
    }
}