package com.example.dps_d.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookingDto {

    private Long flightId;
    private String fareConditions;
    private String seatNo;
    private Float price;
    private String passengerId;
    private String passengerName;
    private String phone;
    private String email;

}
