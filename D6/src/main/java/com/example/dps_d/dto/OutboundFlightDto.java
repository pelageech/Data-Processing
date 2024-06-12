package com.example.dps_d.dto;

import java.time.DayOfWeek;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutboundFlightDto {

    private DayOfWeek day;
    private Date arrivalTime;
    private String flightNo;
    private String destination;


}