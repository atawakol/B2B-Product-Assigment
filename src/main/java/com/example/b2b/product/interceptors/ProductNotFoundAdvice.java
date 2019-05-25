package com.example.b2b.product.interceptors;

import com.example.b2b.product.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Interceptor that handle product not found exceptions.
 */
@ControllerAdvice
public class ProductNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }
}
