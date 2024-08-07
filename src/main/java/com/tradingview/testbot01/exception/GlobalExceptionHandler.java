package com.tradingview.testbot01.exception;

import com.tradingview.testbot01.exception.TradingExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderExecutionException.class)
    public ResponseEntity<String> handleOrderExecutionException(OrderExecutionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<String> handleInvalidOrderException(InvalidOrderException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeConnectionException.class)
    public ResponseEntity<String> handleExchangeConnectionException(ExchangeConnectionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}