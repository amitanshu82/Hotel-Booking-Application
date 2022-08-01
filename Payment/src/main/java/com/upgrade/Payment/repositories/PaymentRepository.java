package com.upgrade.Payment.repositories;

import com.upgrade.Payment.entities.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository <TransactionDetailsEntity, Integer>{
    public TransactionDetailsEntity findByBookingId(int bookingId);
}
