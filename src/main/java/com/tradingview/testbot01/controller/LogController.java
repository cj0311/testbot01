package com.tradingview.testbot01.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public List<String> getRecentLogs() {
        try {
            return logService.getRecentLogs();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read logs", e);
        }
    }
}