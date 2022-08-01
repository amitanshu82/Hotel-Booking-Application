package com.upgrade.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * BookingInfo Entity
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="booking")
public class BookingInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId ;

    @Column( nullable = true )
    private Date fromDate ;

    @Column( nullable = true )
    private Date toDate ;

    @Column( nullable = true)
    private String aadharNumber ;

    @Column
    private int numOfRooms ;

    @Column
    private String roomNumbers;

    @Column(nullable = false)
    private int roomPrice;

    @Column(columnDefinition = "integer default 0")
    private int transactionId;

    @Column(nullable = true)
    private Date bookedOn;

}
