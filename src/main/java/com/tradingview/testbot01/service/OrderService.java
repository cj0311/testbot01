package com.tradingview.testbot01.service;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.exchange.TradingPlatform;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class OrderService {

    private TradingPlatformService platformService;

    private LoggingService loggingService;

    public OrderResult processOrder(TradingViewOrder order) {
        TradingPlatform platform = platformService.getPlatform(order.getExchange());
        platform.initInfo(order);

        OrderResult result;
        if (order.isCrypto()) {
            if (order.isEntry()) {
                result = platform.marketEntry(order);
            } else if (order.isClose()) {
                result = platform.marketClose(order);
            } else if (order.isBuy()) {
                result = platform.marketBuy(order);
            } else if (order.isSell()) {
                result = platform.marketSell(order);
            } else {
                throw new IllegalArgumentException("Invalid order type for crypto");
            }
        } else if (order.isStock()) {
            result = platform.createOrder(order);
        } else {
            throw new IllegalArgumentException("Unsupported asset type");
        }

        CompletableFuture.runAsync(() -> loggingService.logOrderResult(order.getExchange(), result, order));

        return result;
    }
}