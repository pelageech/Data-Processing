package com.example.dps_d.repository;

import com.example.dps_d.entity.TicketFlight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketFlightRepository extends JpaRepository<TicketFlight, String> {

    @Query("""
            select distinct
            max(t.amount)
            from TicketFlight t
            join Flight f on t.flightId = f.id
            where f.flightNo = :flightNo and t.fareConditions = :fareConditions
            group by f.flightNo, t.fareConditions
            """)
    Float findTicketFlightByFlightNoAndFare(String flightNo, String fareConditions);

    @Query("""
             select max(p.amount)
            from Flight f1
                     join Pricing p on p.flightNo = f1.flightNo and p.fareConditions = :fareConditions
            where f1.aircraftCode = :aircraftCode
              and f1.departureAirport = :departureAirport
              and f1.arrivalAirport = :arrivalAirport
             """)
    Float predictCost(String departureAirport, String arrivalAirport, String aircraftCode, String fareConditions);

    @Query("""
            select tf from TicketFlight tf
            where tf.ticketNo = :ticketNo
            """)
    TicketFlight findTicketFlightByTicketNo(String ticketNo);

    @Query("""
            select tf
            from TicketFlight  tf
            join Ticket t on tf.ticketNo = t.ticketNo
            and t.bookRef = :bookRef
            """)
    List<TicketFlight> findByBookRef(String bookRef);
}
