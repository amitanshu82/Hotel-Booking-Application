package com.upgrade.Payment.services;

import com.upgrade.Payment.dtos.TransactionDetailsDto;
import com.upgrade.Payment.entities.TransactionDetailsEntity;
import com.upgrade.Payment.exceptions.DuplicateTransactionException;
import com.upgrade.Payment.exceptions.NoTransactionFoundException;
import com.upgrade.Payment.models.TransactionRequest;
import com.upgrade.Payment.repositories.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    @Transactional
    public int savePaymentTransaction(TransactionRequest transactionRequest) {
        TransactionDetailsEntity transactionDetailsEntity = getTransactionDetailsForBookingId(transactionRequest.getBookingId());
        if(transactionDetailsEntity != null){
            throw new DuplicateTransactionException();
        }
        transactionDetailsEntity = new TransactionDetailsEntity();
        transactionDetailsEntity.setPaymentMode(transactionRequest.getPaymentMode());
        transactionDetailsEntity.setBookingId(transactionRequest.getBookingId());
        transactionDetailsEntity.setUpiId(transactionRequest.getUpiId());
        transactionDetailsEntity.setCaredNumber(transactionRequest.getCardNumber());
        paymentRepository.save(transactionDetailsEntity);
        return transactionDetailsEntity.getTransactionId();
    }

    @Override
    public TransactionDetailsDto getTransactionDetails(int transactionId) {
        TransactionDetailsEntity transaction = paymentRepository.findById(transactionId).orElseThrow(NoTransactionFoundException::new);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        TransactionDetailsDto response = mapper.map(transaction, TransactionDetailsDto.class);
        response.setId(transaction.getTransactionId());
        response.setBookingId(transaction.getBookingId());
        return response;
    }

    public TransactionDetailsEntity getTransactionDetailsForBookingId(int bookingId){
        return paymentRepository.findByBookingId(bookingId);
    }
}

