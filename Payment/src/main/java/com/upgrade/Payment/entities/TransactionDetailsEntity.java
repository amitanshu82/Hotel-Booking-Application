package com.upgrade.Payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="transaction")
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private int transactionId;
    private String paymentMode;
    @Column(nullable = false)
    private int bookingId;
    @Column(nullable = true)
    private String upiId;
    @Column(nullable = true,length = 12)
    private String caredNumber;

}
