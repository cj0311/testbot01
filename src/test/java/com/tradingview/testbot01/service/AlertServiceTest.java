package com.tradingview.testbot01.service;

import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AlertServiceTest {
//
//    @Mock
//    private AlertRepository alertRepository;
//
//    @InjectMocks
//    private AlertService alertService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testProcessAlert() {
//        TradingViewAlert alert = new TradingViewAlert();
//        alert.setSymbol("BTC/USDT");
//        alert.setAction("BUY");
//
//        alertService.processAlert(alert);
//
//        verify(alertRepository, times(1)).save(alert);
//    }
//
//    @Test
//    void testGetRecentAlerts() {
//        List<TradingViewAlert> mockAlerts = Arrays.asList(
//                new TradingViewAlert(), new TradingViewAlert()
//        );
//
//        when(alertRepository.findTop10ByOrderByCreatedAtDesc()).thenReturn(mockAlerts);
//
//        List<TradingViewAlert> result = alertService.getRecentAlerts(10);
//
//        assertEquals(2, result.size());
//        verify(alertRepository, times(1)).findTop10ByOrderByCreatedAtDesc();
//    }
}