package com.tradingview.testbot01.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingview.testbot01.domain.OrderResult;
import com.tradingview.testbot01.domain.TradingViewOrder;
import com.tradingview.testbot01.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderService orderService;

    private ObjectMapper objectMapper;

    @PostMapping("/order")
    public ResponseEntity<String> processOrder(@RequestBody String payload) {
        try {

            log.info("alertMessage = " + payload);
            Map<String, Object> orderMap = objectMapper.readValue(payload, Map.class);
            TradingViewOrder order = convertToTradingViewOrder(orderMap);
            OrderResult result = orderService.processOrder(order);
            return ResponseEntity.ok("Order processed successfully: " + result.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing order: " + e.getMessage());
        }
    }

    private TradingViewOrder convertToTradingViewOrder(Map<String, Object> orderMap) {
        TradingViewOrder order = new TradingViewOrder();
        order.setExchange((String) orderMap.get("exchange"));
        order.setBase((String) orderMap.get("base"));
        order.setQuote((String) orderMap.get("quote"));
        order.setSide((String) orderMap.get("side"));
        order.setAmount(orderMap.get("amount") != null ? Double.parseDouble(orderMap.get("amount").toString()) : null);
        order.setPrice(orderMap.get("price") != null ? Double.parseDouble(orderMap.get("price").toString()) : null);
        order.setType((String) orderMap.get("type"));
        order.setLeverage(orderMap.get("leverage") != null ? Integer.parseInt(orderMap.get("leverage").toString()) : null);
        order.setPercent(orderMap.get("percent") != null ? Double.parseDouble(orderMap.get("percent").toString()) : null);
        order.setUnifiedSymbol(order.getBase() + "/" + order.getQuote());
        return order;
    }
}