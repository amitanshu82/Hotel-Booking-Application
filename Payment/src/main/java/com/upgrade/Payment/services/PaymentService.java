package com.upgrade.Payment.services;

import com.upgrade.Payment.dtos.TransactionDetailsDto;
import com.upgrade.Payment.models.TransactionRequest;

public interface PaymentService {
    int savePaymentTransaction(TransactionRequest transactionRequest);
    TransactionDetailsDto getTransactionDetails(int bookingId);
}
