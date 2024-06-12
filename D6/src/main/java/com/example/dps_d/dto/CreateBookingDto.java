package com.example.dps_d.dto;

import java.util.List;
import lombok.Data;

@Data
public class CreateBookingDto {

    private List<Long> flights;
    private String fareConditions;
    private String passengerId;
    private String passengerName;
    private String phone;
    private String email;

}
