package com.tradingview.testbot01.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
//@Entity
//@Table(name = "trading_view_alerts")
public class TradingViewAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @Column(name = "time_interval")
    private String interval;

    private String strategy;
    private String action;
    private double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and setters
}