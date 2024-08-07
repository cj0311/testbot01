package com.tradingview.testbot01.example;
import org.knowm.xchange.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/binance")
public class BinanceController {

    @Autowired
    private BinanceService binanceService;

    @PostMapping("/order")
    public String placeOrder(@RequestParam String base,
                             @RequestParam String quote,
                             @RequestParam Order.OrderType side,
                             @RequestParam BigDecimal amount) {
        try {
            return binanceService.placeMarketOrder(base, quote, side, amount);
        } catch (IOException e) {
            return "Error placing order: " + e.getMessage();
        }
    }
}
