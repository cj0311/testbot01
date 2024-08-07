package com.tradingview.testbot01.example;

import lombok.RequiredArgsConstructor;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@RequiredArgsConstructor
@Configuration
public class BinanceConfig {

    private final Environment env;
    @Bean
    public Exchange binanceExchange() {
        Exchange binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());

        String apiKey = env.getProperty("BINANCE_API_KEY");
        String secret = env.getProperty("BINANCE_SECRET");

        binance.getExchangeSpecification().setApiKey(apiKey);
        binance.getExchangeSpecification().setSecretKey(secret);

        binance.applySpecification(binance.getExchangeSpecification());
        return binance;
    }
}