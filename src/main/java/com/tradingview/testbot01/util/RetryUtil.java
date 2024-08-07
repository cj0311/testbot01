package com.tradingview.testbot01.util;


import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
public class RetryUtil {
    private final RetryTemplate retryTemplate;

    public RetryUtil() {
        this.retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        this.retryTemplate.setRetryPolicy(retryPolicy);
    }

    public <T> T retry(Callable<T> task) throws Exception {
        return this.retryTemplate.execute(context -> task.call());
    }
}