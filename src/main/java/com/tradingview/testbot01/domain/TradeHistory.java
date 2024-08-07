package com.tradingview.testbot01.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trade_history")
public class TradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String side;
    private double amount;
    private double price;
    private String orderId;
    private LocalDateTime tradeTime;

    @PrePersist
    protected void onCreate() {
        tradeTime = LocalDateTime.now();
    }
}