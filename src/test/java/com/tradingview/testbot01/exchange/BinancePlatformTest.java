package com.tradingview.testbot01.exchange;

import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.util.RetryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BinancePlatformTest {

    @Mock
    private Environment env;

    @Mock
    private RetryUtil retryUtil;

    private BinancePlatform binancePlatform;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("BINANCE_API_KEY")).thenReturn("testApiKey");
        when(env.getProperty("BINANCE_SECRET")).thenReturn("testSecret");
        binancePlatform = new BinancePlatform(env, retryUtil);
    }

    @Test
    public void testMarketEntry() throws Exception {
        TradingViewOrder order = new TradingViewOrder();
        order.setBase("BTC");
        order.setQuote("USDT");
        order.setSide("BUY");
        order.setAmount(1.0);

        OrderResult mockResult = new OrderResult("123");
        when(retryUtil.retry(any())).thenReturn(mockResult);

        OrderResult result = binancePlatform.marketEntry(order);

        assertNotNull(result);
        assertEquals("123", result.getOrderId());
    }

    // Add more tests for other methods...
}