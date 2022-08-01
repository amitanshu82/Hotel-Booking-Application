package com.upgrade.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoDto {
    private int id ;
    private Date fromDate ;
    private Date toDate ;
    private String aadharNumber ;
    private String roomNumbers;
    private int roomPrice;
    private int transactionId;
    private Date bookedOn;
}
