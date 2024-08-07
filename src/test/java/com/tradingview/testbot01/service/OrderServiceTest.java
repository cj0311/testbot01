package com.tradingview.testbot01.service;

import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.exchange.TradingPlatform;
import com.tradingview.testbot01.exception.TradingExceptions.OrderExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

//    @Mock
//    private TradingPlatformService platformService;
//
//    @Mock
//    private LoggingService loggingService;
//
//    @Mock
//    private TradingPlatform tradingPlatform;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testProcessOrder_Crypto() {
//        TradingViewOrder order = new TradingViewOrder();
//        order.setExchange("BINANCE");
//        order.setBase("BTC");
//        order.setQuote("USDT");
//        order.setSide("BUY");
//
//        when(platformService.getPlatform("BINANCE")).thenReturn(tradingPlatform);
//        when(tradingPlatform.marketBuy(order)).thenReturn(new OrderResult("123"));
//
//        OrderResult result = orderService.processOrder(order);
//
//        assertNotNull(result);
//        assertEquals("123", result.getOrderId());
//        verify(loggingService).logOrderResult(eq("BINANCE"), any(OrderResult.class), eq(order));
//    }
//
//    @Test
//    public void testProcessOrder_InvalidAssetType() {
//        TradingViewOrder order = new TradingViewOrder();
//        order.setExchange("UNKNOWN");
//
//        when(platformService.getPlatform("UNKNOWN")).thenReturn(tradingPlatform);
//        when(tradingPlatform.createOrder(order)).thenThrow(new UnsupportedOperationException("Unsupported asset type"));
//
//        assertThrows(OrderExecutionException.class, () -> orderService.processOrder(order));
//    }
}