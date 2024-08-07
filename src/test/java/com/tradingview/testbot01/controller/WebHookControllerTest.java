package com.tradingview.testbot01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.service.AlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(WebHookController.class)
public class WebHookControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AlertService alertService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testReceiveWebhook() throws Exception {
//        TradingViewAlert alert = new TradingViewAlert();
//        alert.setSymbol("BTC/USDT");
//        alert.setAction("BUY");
//
//        doNothing().when(alertService).processAlert(any(TradingViewAlert.class));
//
//        mockMvc.perform(post("/webhook")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(alert)))
//                .andExpect(status().isOk());
//    }
}