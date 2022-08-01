package com.upgrade.booking.controllers;

import com.upgrade.booking.exceptions.BookingIdNotMatchingException;
import com.upgrade.booking.models.BookingRequest;
import com.upgrade.booking.services.BookingService;
import com.upgrade.booking.dtos.BookingInfoDto;
import com.upgrade.booking.models.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    /**
     * Method to handle /booking API
     * @param bookingRequest
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoDto> saveBooking(@RequestBody BookingRequest bookingRequest){
        return new ResponseEntity<>(bookingService.saveBooking(bookingRequest), HttpStatus.OK);
    }

    /**
     * Method to handle /booking/{bookingId}/transaction API and call payment service internally to get and return the transaction id
     * @param bookingId
     * @param transactionRequest
     * @return
     */
    @PostMapping(value = "/{bookingId}/transaction",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoDto> saveTransactionForBookingId(@PathVariable(value = "bookingId") int bookingId,
                                                                      @RequestBody TransactionRequest transactionRequest){
        if (bookingId != transactionRequest.getBookingId())
            throw new BookingIdNotMatchingException();
        return new ResponseEntity<>(bookingService.saveTransactionForBookingId(bookingId,transactionRequest), HttpStatus.OK);
    }
}
