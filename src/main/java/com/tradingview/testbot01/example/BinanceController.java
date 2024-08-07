package com.tradingview.testbot01.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.dto.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/binance")
public class BinanceController {


    private BinanceServiceExample binanceServiceExample;
    private ObjectMapper objectMapper;
//
//    @PostMapping("/order")
//    public String placeOrder(@RequestParam String base,
//                             @RequestParam String quote,
//                             @RequestParam Order.OrderType side,
//                             @RequestParam BigDecimal amount) {
//        try {
//            return binanceService.placeMarketOrder(base, quote, side, amount);
//        } catch (IOException e) {
//            return "Error placing order: " + e.getMessage();
//        }
//    }


    @PostMapping("/order")
    public String placeOrder(@RequestBody String orderDetails) {
        try {log.info("orderDetails : " + orderDetails);
            OrderRequest orderRequest = objectMapper.readValue(orderDetails, OrderRequest.class);
            String base = orderRequest.getBase();
            String quote = orderRequest.getQuote();
            log.info("222");

            Order.OrderType side;
            switch (orderRequest.getSide().toLowerCase()) {
                case "bid":
                    side = Order.OrderType.BID;
                    break;
                case "ask":
                    side = Order.OrderType.ASK;
                    break;
                case "exit_bid":
                    side = Order.OrderType.EXIT_BID;
                    break;
                case "exit_ask":
                    side = Order.OrderType.EXIT_ASK;
                    break;
                default:
                    return "Invalid order side: " + orderRequest.getSide();
            }
            log.info("side.toString() : " + side.toString());
            BigDecimal amount = new BigDecimal(orderRequest.getAmount());
            log.info("amount = " + amount);
            return binanceServiceExample.placeMarketOrder(base, quote, side, amount);
        } catch (IOException e) {
            return "Error placing order: " + e.getMessage();
        }
    }

    @PostMapping("/ticker")
    public String getTickerPrice(@RequestBody String tickerDetails) {
        try {
            log.info("tickerDetails : " + tickerDetails);
            TickerRequest tickerRequest = objectMapper.readValue(tickerDetails, TickerRequest.class);
            String base = tickerRequest.getBase();
            String quote = tickerRequest.getQuote();

            BigDecimal price = binanceServiceExample.getTickerPrice(base, quote);
            return "Last price for " + base + "/" + quote + ": " + price.toPlainString();
        } catch (IOException e) {
            return "Error fetching ticker price: " + e.getMessage();
        }
    }
}
