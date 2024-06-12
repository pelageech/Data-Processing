package com.example.dps_d.controller;

import com.example.dps_d.dto.BookingsResponseDto;
import com.example.dps_d.dto.CheckInDto;
import com.example.dps_d.dto.CreateBookingDto;
import com.example.dps_d.dto.InboundFlightDto;
import com.example.dps_d.dto.OutboundFlightDto;
import com.example.dps_d.dto.RoutesDto;
import com.example.dps_d.entity.Airport;
import com.example.dps_d.entity.BoardingPass;
import com.example.dps_d.service.AirportService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService service;

    @GetMapping("/departure/cities")
    public List<String> getDepartureCities(@RequestParam String language) {
        return service.getDepartureCities(language);
    }

    @GetMapping("/arrival/cities")
    public List<String> getArrivalCities(@RequestParam String language) {
        return service.getArrivalCities(language);
    }

    @GetMapping("/departure/airports")
    public List<Airport> getDepartureAirports(@RequestParam String language) {
        return service.getDepartureAirports(language);
    }

    @GetMapping("/arrival/airports")
    public List<Airport> getArrivalAirports(@RequestParam String language) {
        return service.getArrivalAirports(language);
    }

    @GetMapping("/airports/{city}")
    public List<Airport> getAirports(@PathVariable String city) {
        return service.getAirports(city);
    }

    @GetMapping("/inbound")
    public List<InboundFlightDto> getInboundFlights(@RequestParam String airport) { //can be code or name
        return service.getInboundFlights(airport);
    }

    @GetMapping("/outbound")
    public List<OutboundFlightDto> getOutboundFlights(@RequestParam String airport) { //can be code or name
        return service.getOutboundFlights(airport);
    }

    @GetMapping("/departure")
    public List<RoutesDto> getRoutes(@RequestParam String point1,
                                     @RequestParam String point2,
                                     @RequestParam LocalDateTime date,
                                     @RequestParam String fareConditions,
                                     @RequestParam Integer conn) {
        return service.getRoutes(point1, point2, date, fareConditions, conn);
    }

    @PostMapping("/booking")
    public BookingsResponseDto createBooking(@RequestBody CreateBookingDto booking) {
        return service.createBooking(booking);
    }

    @PostMapping("/check-in")
    public List<BoardingPass> createCheckIn(@RequestBody CheckInDto checkInDto) {
        return service.createCheckIn(checkInDto);
    }

    @GetMapping("/predict")
    public Float predict(@RequestParam String departureAirport,
                         @RequestParam String arrivalAirport,
                         @RequestParam String aircraftCode,
                         @RequestParam String fareConditions) {
        return service.predictCost(departureAirport, arrivalAirport, aircraftCode, fareConditions);
    }
}
