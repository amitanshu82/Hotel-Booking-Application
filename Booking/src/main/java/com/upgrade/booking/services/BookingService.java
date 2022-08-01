package com.upgrade.booking.services;

import com.upgrade.booking.dtos.BookingInfoDto;
import com.upgrade.booking.models.BookingRequest;
import com.upgrade.booking.models.TransactionRequest;

public interface BookingService {
    BookingInfoDto saveBooking(BookingRequest bookingRequest);

     BookingInfoDto saveTransactionForBookingId(int bookingId,TransactionRequest transactionRequest);
}
