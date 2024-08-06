package com.tradingview.testbot01.controller;

import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.service.AlertService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class WebHookController {

    private AlertService alertService;

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody TradingViewAlert alert) {
        alertService.processAlert(alert);
        return ResponseEntity.ok("Webhook received and processed");
    }

}
