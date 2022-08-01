package com.upgrade.booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "paymentMode is required")
    private String paymentMode;
    @NotBlank(message = "bookingId is required")
    private int bookingId;
    private String upiId;
    private String cardNumber;
}
