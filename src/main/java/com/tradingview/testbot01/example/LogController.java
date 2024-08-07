package com.tradingview.testbot01.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public String getLogs(Model model) {
        try {
            List<String> logs = logService.getRecentLogs();
            model.addAttribute("logs", logs);
        } catch (IOException e) {
            model.addAttribute("error", "Error reading logs: " + e.getMessage());
        }
        return "logs";
    }
}