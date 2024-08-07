package com.tradingview.testbot01.configuration;

import com.tradingview.testbot01.exchange.BinancePlatform;
import com.tradingview.testbot01.exchange.TradingPlatform;
import com.tradingview.testbot01.service.TradingPlatformService;
import com.tradingview.testbot01.util.RetryUtil;
import org.knowm.xchange.binance.BinanceExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.HashMap;

@Configuration
public class ExchangeConfig {

    private final Environment env;
    private final RetryUtil retryUtil;

    public ExchangeConfig(Environment env, RetryUtil retryUtil) {
        this.env = env;
        this.retryUtil = retryUtil;
    }

    @Bean
    public BinancePlatform binancePlatform() {
        return new BinancePlatform(env, retryUtil);
    }

    @Bean
    public TradingPlatformService tradingPlatformService(BinancePlatform binancePlatform) {
        Map<String, TradingPlatform> platforms = new HashMap<>();
        platforms.put("BINANCE", binancePlatform);
        return new TradingPlatformService(platforms);
    }
}
