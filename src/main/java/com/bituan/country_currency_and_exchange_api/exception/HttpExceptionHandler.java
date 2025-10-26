package com.bituan.country_currency_and_exchange_api.exception;

import com.bituan.country_currency_and_exchange_api.model.HttpExceptionMessageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<HttpExceptionMessageModel> httpException(HttpException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getCustomMessage());
    }
}
