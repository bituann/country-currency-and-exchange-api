package com.bituan.country_currency_and_exchange_api.exception;

import com.bituan.country_currency_and_exchange_api.model.HttpExceptionMessageModel;
import org.springframework.http.HttpStatus;

public class HttpException extends Exception{
    private HttpStatus status;
    private HttpExceptionMessageModel customMessage;

    public HttpException (HttpStatus status, String error, String details) {
        this.status = status;
        this.customMessage = new HttpExceptionMessageModel(error, details);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpExceptionMessageModel getCustomMessage() {
        return customMessage;
    }
}
