package com.upgrade.Payment.controllers;

import com.upgrade.Payment.dtos.TransactionDetailsDto;
import com.upgrade.Payment.models.TransactionRequest;
import com.upgrade.Payment.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Integer> saveTransaction(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(paymentService.savePaymentTransaction(transactionRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getTranscationDetails(@PathVariable(value = "transactionId" ) int transactionId){
        return new ResponseEntity<>(paymentService.getTransactionDetails(transactionId),HttpStatus.OK);
    }

}
