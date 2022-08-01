package com.upgrade.booking.advices;

import com.upgrade.booking.exceptions.BookingIdNotMatchingException;
import com.upgrade.booking.exceptions.DuplicateTransactionException;
import com.upgrade.booking.exceptions.InvalidPaymentMethodException;
import com.upgrade.booking.exceptions.NoBookingFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateTransactionException.class})
    protected ResponseEntity<Object> duplicateTransactionExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Payment already completed for this booking id";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = {NoBookingFoundException.class})
    protected ResponseEntity<Object> noBookingFoundExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = " Invalid Booking Id ";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {InvalidPaymentMethodException.class})
    protected ResponseEntity<Object> invalidPaymentModeExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid mode of payment";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {BookingIdNotMatchingException.class})
    protected ResponseEntity<Object> bookingIdNotMatchingExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Booking Id is not same in request header and request body";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    protected ResponseEntity<Object> httpClientErrorExceptionHandler(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getLocalizedMessage();//"HttpClientErrorException occured";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
