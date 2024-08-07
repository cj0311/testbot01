package com.tradingview.testbot01.exchange;

import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.util.RetryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BinancePlatformTest {

    @Mock
    private Environment env;

    @Mock
    private RetryUtil retryUtil;

    @Mock
    private Exchange exchange;

    @Mock
    private TradeService tradeService;

    private BinancePlatform binancePlatform;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("BINANCE_API_KEY")).thenReturn("testApiKey");
        when(env.getProperty("BINANCE_SECRET")).thenReturn("testSecret");
        when(exchange.getTradeService()).thenReturn(tradeService);

        // Mock ExchangeFactory to return our mocked Exchange
        try (var mockedExchangeFactory = mockStatic(ExchangeFactory.class)) {
            mockedExchangeFactory.when(() -> ExchangeFactory.INSTANCE.createExchange(any(ExchangeSpecification.class)))
                    .thenReturn(exchange);

            binancePlatform = new BinancePlatform(env, retryUtil);
        }
    }

    @Test
    public void testMarketEntry() throws Exception {
        TradingViewOrder order = new TradingViewOrder();
        order.setBase("BTC");
        order.setQuote("USDT");
        order.setSide("BUY");
        order.setAmount(1.0);

        when(tradeService.placeMarketOrder(any(MarketOrder.class))).thenReturn("123");
        when(retryUtil.retry(any())).thenAnswer(invocation -> ((java.util.concurrent.Callable<?>) invocation.getArgument(0)).call());

        OrderResult result = binancePlatform.marketEntry(order);

        assertNotNull(result);
        assertEquals("123", result.getOrderId());
        verify(tradeService).placeMarketOrder(any(MarketOrder.class));
    }

    @Test
    public void testSetLeverage() throws Exception {
        // This test might need to be adjusted based on the actual implementation
        assertDoesNotThrow(() -> binancePlatform.setLeverage(5, "BTCUSDT"));
    }

    @Test
    public void testGetAmount() {
        TradingViewOrder order = new TradingViewOrder();
        order.setAmount(2.5);

        double amount = binancePlatform.getAmount(order);

        assertEquals(2.5, amount, 0.001);
    }

    // Add more tests for other methods as needed
}