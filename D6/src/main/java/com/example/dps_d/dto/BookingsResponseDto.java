package com.example.dps_d.dto;

import com.example.dps_d.entity.TicketFlight;
import java.util.List;
import lombok.Data;

@Data
public class BookingsResponseDto {

    private List<TicketFlight> ticketFlights;
    private String bookRef;
}
