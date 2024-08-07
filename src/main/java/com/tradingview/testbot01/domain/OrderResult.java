package com.tradingview.testbot01.domain;

import lombok.Data;
import org.knowm.xchange.dto.trade.UserTrade;

@Data
public class OrderResult {
    private String orderId;
    private String status;
    private double executedQty;
    private double averagePrice;


    public OrderResult(String orderId) {
        this.orderId = orderId;
        this.status = "SUBMITTED"; // 초기 상태
    }

    @Override
    public String toString() {
        return String.format("OrderResult(orderId=%s, status=%s, executedQty=%.8f, averagePrice=%.8f)",
                orderId, status, executedQty, averagePrice);
    }


}
