package com.tradingview.testbot01.service;
import com.tradingview.testbot01.domain.TradeHistory;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.exception.TradingExceptions;
import com.tradingview.testbot01.exception.TradingExceptions.InvalidOrderException;
import com.tradingview.testbot01.exception.TradingExceptions.OrderExecutionException;
import com.tradingview.testbot01.exchange.TradingPlatform;
import com.tradingview.testbot01.repository.TradeHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class OrderService {


    private TradingPlatformService platformService;
    private LoggingService loggingService;
    private TradeHistoryRepository tradeHistoryRepository;

    public OrderResult processOrder(TradingViewOrder order) {
        try {
            TradingPlatform platform = platformService.getPlatform(order.getExchange());
            platform.initInfo(order);

            OrderResult result;
            if (order.isCrypto()) {
                result = processCryptoOrder(order, platform);
            } else if (order.isStock()) {
                result = platform.createOrder(order);
            } else {
                throw new InvalidOrderException("Unsupported asset type");
            }

            loggingService.logOrderResult(order.getExchange(), result, order);
            return result;
        } catch (Exception e) {
            loggingService.logError("Error processing order", e);
            throw new OrderExecutionException("Failed to process order: " + e.getMessage());
        }
    }

    private OrderResult processCryptoOrder(TradingViewOrder order, TradingPlatform platform) {
        if (order.isEntry()) {
            return platform.marketEntry(order);
        } else if (order.isClose()) {
            return platform.marketClose(order);
        } else if (order.isBuy()) {
            return platform.marketBuy(order);
        } else if (order.isSell()) {
            return platform.marketSell(order);
        } else {
            throw new InvalidOrderException("Invalid order type for crypto");
        }
    }


    private void saveTradeHistory(TradingViewOrder order, OrderResult result) {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setSymbol(order.getUnifiedSymbol());
        tradeHistory.setSide(order.getSide());
        tradeHistory.setAmount(order.getAmount());
        tradeHistory.setPrice(result.getAveragePrice());
        tradeHistory.setOrderId(result.getOrderId());
        tradeHistoryRepository.save(tradeHistory);
    }
}