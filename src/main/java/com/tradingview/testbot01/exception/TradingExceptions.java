package com.tradingview.testbot01.exception;


public class TradingExceptions {

    public static class OrderExecutionException extends RuntimeException {
        public OrderExecutionException(String message) {
            super(message);
        }
    }

    public static class InvalidOrderException extends RuntimeException {
        public InvalidOrderException(String message) {
            super(message);
        }
    }

    public static class ExchangeConnectionException extends RuntimeException {
        public ExchangeConnectionException(String message) {
            super(message);
        }
    }
}