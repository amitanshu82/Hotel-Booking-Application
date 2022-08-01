package com.upgrade.Payment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private int bookingId;
    private String paymentMode;
    private String upiId;
    private String cardNumber;
}
