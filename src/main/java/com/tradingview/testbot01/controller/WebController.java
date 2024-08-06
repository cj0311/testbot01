package com.tradingview.testbot01.controller;


import com.tradingview.testbot01.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WebController {


    private final AlertService alertService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("alerts", alertService.getRecentAlerts(10));
        return "home";
    }
}
