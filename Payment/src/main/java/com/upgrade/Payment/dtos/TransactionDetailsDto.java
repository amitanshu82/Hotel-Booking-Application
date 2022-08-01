package com.upgrade.Payment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDto {
    private int id;
    private String paymentMode;
    private int bookingId;
    private String upiId;
    private String caredNumber;
}
