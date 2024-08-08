package com.tradingview.testbot01.basic;


import lombok.AllArgsConstructor;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.UserTrades;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/binance")
@AllArgsConstructor
public class BinanceControllerBasic {

    private AccountInfoServiceBasic accountInfoService;

    private MarketDataServiceBasic marketDataService;

    private OrderServiceBasic orderService;

    private OrderCancellationServiceBasic orderCancellationService;

    private TradeHistoryServiceBasic tradeHistoryService;


    @GetMapping
    public String showDashboard() {
        return "binance/dashboard";
    }

    @GetMapping("/account-info")
    public String getAccountInfo(Model model) throws IOException {
        AccountInfo accountInfo = accountInfoService.getAccountInfo();
        model.addAttribute("accountInfo", accountInfo);
        return "binance/account-info";
    }

    @GetMapping("/ticker")
    public String getTicker(@RequestParam(required = false) String symbol, Model model) throws IOException {
        if (symbol != null && !symbol.isEmpty()) {
            Ticker ticker = marketDataService.getTicker(new CurrencyPair(symbol));
            model.addAttribute("ticker", ticker);
        }
        return "binance/ticker";
    }

    @PostMapping("/order/limit")
    public String placeLimitOrder(@RequestParam String symbol,
                                  @RequestParam String type,
                                  @RequestParam BigDecimal amount,
                                  @RequestParam BigDecimal price,
                                  Model model) throws IOException {
        String orderId = orderService.placeLimitOrder(new CurrencyPair(symbol),
                OrderType.valueOf(type),
                amount,
                price);
        model.addAttribute("orderId", orderId);
        return "binance/order-result";
    }

    @PostMapping("/order/market")
    public String placeMarketOrder(@RequestParam String symbol,
                                   @RequestParam String type,
                                   @RequestParam BigDecimal amount,
                                   Model model) throws IOException {
        String orderId = orderService.placeMarketOrder(new CurrencyPair(symbol),
                OrderType.valueOf(type),
                amount);
        model.addAttribute("orderId", orderId);
        return "binance/order-result";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam String orderId, Model model) throws IOException {
        boolean result = orderCancellationService.cancelOrder(orderId);
        model.addAttribute("cancelResult", result);
        return "binance/cancel-result";
    }

    @GetMapping("/trades")
    public String getTradeHistory(Model model) throws IOException {
        UserTrades trades = tradeHistoryService.getTradeHistory();
        model.addAttribute("trades", trades);
        return "binance/trade-history";
    }
}
