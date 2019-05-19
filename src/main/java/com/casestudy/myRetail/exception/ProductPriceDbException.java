package com.casestudy.myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductPriceDbException extends RuntimeException {

    public ProductPriceDbException(String message) {
        super(message);
    }
}
