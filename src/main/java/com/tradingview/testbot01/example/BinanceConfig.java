package com.tradingview.testbot01.example;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfig {

    @Bean
    public Exchange binanceExchange() {
        Exchange binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
        binance.getExchangeSpecification().setApiKey("your-api-key");
        binance.getExchangeSpecification().setSecretKey("your-secret-key");
        binance.applySpecification(binance.getExchangeSpecification());
        return binance;
    }
}