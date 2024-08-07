package com.tradingview.testbot01.service;
import com.tradingview.testbot01.exchange.TradingPlatform;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class TradingPlatformService {
    private final Map<String, TradingPlatform> platforms;

    public TradingPlatformService(Map<String, TradingPlatform> platforms) {
        this.platforms = platforms;
    }

    public TradingPlatform getPlatform(String platformName) {
        TradingPlatform platform = platforms.get(platformName.toUpperCase());
        if (platform == null) {
            throw new IllegalArgumentException("Unsupported trading platform: " + platformName);
        }
        return platform;
    }
}
