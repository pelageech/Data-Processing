package com.example.dps_d.repository;

import com.example.dps_d.dto.RoutesModel;
import com.example.dps_d.entity.Flight;
import com.example.dps_d.entity.TicketFlight;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
            select f from Flight f
            join Airport a on f.arrivalAirport = a.code
            where a.name like '%' || :airportName|| '%' or a.code like '%' || :airportName || '%'
            """)
    List<Flight> findInboundFlightsByAirportName(String airportName);

    @Query("""
            select f from Flight f
            join Airport a on f.departureAirport = a.code
            where a.name like '%' || :airportName || '%' or a.code like '%' || :airportName || '%'
            """)
    List<Flight> findOutboundFlightsByAirportName(String airportName);

    @Query(value = """
            select distinct new com.example.dps_d.dto.RoutesModel(1, f1, f1.departureAirport || '->' || f1.arrivalAirport)
            from Flight f1
                     join Airport a1 on a1.code = f1.departureAirport and
                                        (a1.city like '%' || :point1 || '%' or a1.code like '%' || :point1 || '%' or a1.name like '%' || :point1 || '%')
                     join Airport a2 on a2.code = f1.arrivalAirport and
                                        (a2.city like '%' || :point2 || '%' or a2.code like '%' || :point2 || '%' or a2.name like '%' || :point2 || '%')
            where (f1.scheduledDeparture between :dateBefore and :dateAfter)
            """)
    List<RoutesModel> getRoutes1(String point1, String point2, LocalDateTime dateBefore, LocalDateTime dateAfter);

    @Query(value = """     
            select distinct new com.example.dps_d.dto.RoutesModel(2, f1, f2,
                            f1.departureAirport || '->' || f1.arrivalAirport || '->' || f2.arrivalAirport)
            from Flight f1
                     join Flight f2
                          on f1.arrivalAirport = f2.departureAirport and
                             f2.scheduledDeparture between f1.scheduledArrival and :dateAfter
                     join Airport a1 on a1.code = f1.departureAirport and
                                        (a1.city like '%' || :point1 || '%' or a1.code like '%' || :point1 || '%' or a1.name like '%' || :point1 || '%')
                     join Airport a2 on a2.code = f1.arrivalAirport and a2.city not in (a1.city)
                     join Airport a3 on a3.code = f2.arrivalAirport and
                                        (a3.city like '%' || :point2 || '%' or a3.code like '%' || :point2 || '%' or a3.name like '%' || :point2 || '%')
            where (f1.scheduledDeparture between :dateBefore and :dateAfter)
            """)
    List<RoutesModel> getRoutes2(String point1, String point2, LocalDateTime dateBefore, LocalDateTime dateAfter);

    @Query(value = """        
            select distinct new com.example.dps_d.dto.RoutesModel(3, f1, f2, f3,
                            f1.departureAirport || '->' || f1.arrivalAirport || '->' || f2.arrivalAirport || '->' ||
                            f3.arrivalAirport)
            from Flight f1
                     join Flight f2
                          on f1.arrivalAirport = f2.departureAirport and
                             f2.scheduledDeparture between f1.scheduledArrival and :dateAfter
                              and f2.arrivalAirport not in (f1.departureAirport)
                     join Flight f3
                          on f2.arrivalAirport = f3.departureAirport and
                             f3.scheduledDeparture between f2.scheduledArrival and :dateAfter
                              and f3.arrivalAirport not in (f1.departureAirport, f2.departureAirport)
                     join Airport a1 on a1.code = f1.departureAirport and
                                        (a1.city like '%' || :point1 || '%' or a1.code like '%' || :point1 || '%' or a1.name like '%' || :point1 || '%')
                     join Airport a2 on a2.code = f1.arrivalAirport and a2.city not in (a1.city)
                     join Airport a3 on a3.code = f2.arrivalAirport and a3.city not in (a1.city, a2.city)
                     join Airport a4 on a4.code = f3.arrivalAirport and a4.city not in (a1.city, a2.city, a3.city)
                     and (a4.city like '%' || :point2 || '%' or a4.code like '%' || :point2 || '%' or a4.name like '%' || :point2 || '%')
            where (f1.scheduledDeparture between :dateBefore and :dateAfter)
            """)
    List<RoutesModel> getRoutes3(String point1, String point2, LocalDateTime dateBefore, LocalDateTime dateAfter);

    @Query(value = """
            select distinct new com.example.dps_d.dto.RoutesModel(4, f1, f2, f3, f4,
                            f1.departureAirport || '->' || f1.arrivalAirport || '->' || f2.arrivalAirport || '->' ||
                            f3.arrivalAirport || '->' || f4.arrivalAirport)
            from Flight f1
                     join Flight f2
                          on f1.arrivalAirport = f2.departureAirport and
                             f2.scheduledDeparture between f1.scheduledArrival and :dateAfter
                              and f2.arrivalAirport not in (f1.departureAirport)
                     join Flight f3
                          on f2.arrivalAirport = f3.departureAirport and
                             f3.scheduledDeparture between f2.scheduledArrival and :dateAfter
                              and f3.arrivalAirport not in (f1.departureAirport, f2.departureAirport)
                     join Flight f4
                          on f3.arrivalAirport = f4.departureAirport and
                             f4.scheduledDeparture between f3.scheduledArrival and :dateAfter
                              and f4.arrivalAirport not in (f1.departureAirport, f2.departureAirport, f3.departureAirport)
                     join Airport a1 on a1.code = f1.departureAirport and
                                        (a1.city like '%' || :point1 || '%' or a1.code like '%' || :point1 || '%' or a1.name like '%' || :point1 || '%')
                     join Airport a2 on a2.code = f1.arrivalAirport and a2.city not in (a1.city)
                     join Airport a3 on a3.code = f2.arrivalAirport and a3.city not in (a1.city, a2.city)
                     join Airport a4 on a4.code = f3.arrivalAirport and a4.city not in (a1.city, a2.city, a3.city)
                     join Airport a5 on a5.code = f4.arrivalAirport and a5.city not in (a1.city, a2.city, a3.city, a4.city)
                     and (a5.city like '%' || :point2 || '%' or a5.code like '%' || :point2 || '%' or a5.name like '%' || :point2 || '%')
            where f1.scheduledDeparture between :dateBefore and :dateAfter
            """)
    List<RoutesModel> getRoutes4(String point1, String point2, LocalDateTime dateBefore, LocalDateTime dateAfter);

    Flight findFlightById(Long id);

    @Query("""
            select tf from TicketFlight tf
            join Flight f on tf.flightId = f.id and f.id = :flightId
            join Ticket t on t.ticketNo = tf.ticketNo and t.passengerId = :passengerId
            order by tf.flightId
            limit 1
            """)
    TicketFlight findByPassengerId(String passengerId, Long flightId);

}
