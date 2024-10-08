package com.tradingview.testbot01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(OrderController.class)
public class OrderControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testProcessOrder() throws Exception {
//        TradingViewOrder order = new TradingViewOrder();
//        order.setExchange("BINANCE");
//        order.setBase("BTC");
//        order.setQuote("USDT");
//        order.setSide("BUY");
//
//        OrderResult mockResult = new OrderResult("123");
//        when(orderService.processOrder(any(TradingViewOrder.class))).thenReturn(mockResult);
//
//        mockMvc.perform(post("/api/order")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(order)))
//                .andExpect(status().isOk());
//    }
}