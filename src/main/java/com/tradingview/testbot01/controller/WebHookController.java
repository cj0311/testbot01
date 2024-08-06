package com.tradingview.testbot01.controller;

import com.tradingview.testbot01.domain.TradingViewAlert;
import com.tradingview.testbot01.service.AlertService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
public class WebHookController {

    private final AlertService alertService;

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody TradingViewAlert alert) {

        alertService.processAlert(alert);
        return ResponseEntity.ok("Webhook received and processed");
    }



    @PostMapping("/webhooktest")
    public ResponseEntity<String> receiveWebhookTest(String alert) {
        System.out.println("alert = " + alert);
        log.info("alert message : "+ alert);
        return ResponseEntity.ok("alert");
    }
}
