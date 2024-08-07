package com.tradingview.testbot01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class TestBot01Application {

    public static void main(String[] args) {
        SpringApplication.run(TestBot01Application.class, args);
    }

}
