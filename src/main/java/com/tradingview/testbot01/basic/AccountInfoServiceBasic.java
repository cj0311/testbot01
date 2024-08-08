package com.tradingview.testbot01.basic;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountInfoServiceBasic {

    private final Exchange binanceExchange;

    @Autowired
    public AccountInfoServiceBasic(Exchange binanceExchange) {
        this.binanceExchange = binanceExchange;
    }

    public AccountInfo getAccountInfo() throws IOException {
        AccountService accountService = binanceExchange.getAccountService();
        return accountService.getAccountInfo();
    }
}