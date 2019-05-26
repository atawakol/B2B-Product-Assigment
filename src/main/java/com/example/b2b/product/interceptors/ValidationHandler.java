package com.example.b2b.product.interceptors;

import com.example.b2b.product.exceptions.ProductNotFoundException;
import com.example.b2b.product.interceptors.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interceptor that handle product validation exceptions.
 */
@ControllerAdvice
public class ValidationHandler {
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody()
    public List<FieldErrorDTO> processValidationError(MethodArgumentNotValidException ex) {

        return ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody()
    public List<FieldErrorDTO> processConstraintViolationError(ConstraintViolationException ex) {


        return ex.getConstraintViolations().stream()
                .map(cv -> new FieldErrorDTO(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

    }

}
