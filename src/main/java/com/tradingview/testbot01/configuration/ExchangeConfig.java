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

    private Environment env;

//    @Value("${upbit.api.key}")
//    private String upbitApiKey;
//
//    @Value("${upbit.secret.key}")
//    private String upbitSecretKey;

    @Bean
    public BinancePlatform binancePlatform(RetryUtil retryUtil) {

//        String binanceApiKey = env.getProperty("BINANCE_API_KEY");
//        String binanceSecretKey = env.getProperty("BINANCE_SECRET");
        return new BinancePlatform(env,retryUtil);
    }

//    @Bean
//    public UpbitPlatform upbitPlatform(RetryUtil retryUtil) {
//        return new UpbitPlatform(upbitApiKey, upbitSecretKey, retryUtil);
//    }

    @Bean
    public TradingPlatformService tradingPlatformService(BinancePlatform binancePlatform){
//            , UpbitPlatform upbitPlatform) {
        Map<String, TradingPlatform> platforms = new HashMap<>();
        platforms.put("BINANCE", binancePlatform);
//        platforms.put("UPBIT", upbitPlatform);
        return new TradingPlatformService(platforms);
    }
}
