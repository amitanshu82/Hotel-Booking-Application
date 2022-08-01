package com.upgrade.Payment.aspects;

import com.upgrade.Payment.exceptions.DuplicateTransactionException;
import com.upgrade.Payment.exceptions.NoTransactionFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {NoTransactionFoundException.class})
    protected ResponseEntity<Object> noBookingFoundExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No Transaction Found For The Given Transaction Id";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DuplicateTransactionException.class})
    protected ResponseEntity<Object> duplicateTransactionExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Transaction already exist for given Booking Id. Duplicate Transaction not possible";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
