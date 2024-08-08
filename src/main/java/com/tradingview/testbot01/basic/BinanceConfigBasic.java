package com.tradingview.testbot01.basic;

import lombok.RequiredArgsConstructor;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
//@Configuration
public class BinanceConfigBasic {


    private final Environment env;
//    @Bean
    public Exchange binanceExchange() {
        String apiKey = env.getProperty("BINANCE_API_KEY");
        String secretKey = env.getProperty("BINANCE_SECRET");
        Exchange binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
        binance.getExchangeSpecification().setApiKey(apiKey);
        binance.getExchangeSpecification().setSecretKey(secretKey);
        return binance;
    }
}