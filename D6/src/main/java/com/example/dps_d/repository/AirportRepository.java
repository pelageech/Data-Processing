package com.example.dps_d.repository;

import com.example.dps_d.entity.Airport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    @Query("""
            select distinct a.city from Airport a
            join Flight f on a.code = f.departureAirport
            where f is not null
            """)
    List<String> getDepartureCities();

    @Query("""
            select distinct a.city from Airport a
            join Flight f on a.code = f.arrivalAirport
            where f is not null
            """)
    List<String> getArrivalCities();

    @Query("""
            select a from Airport a
            join Flight f on a.code = f.departureAirport
            where f is not null
            """)
    List<Airport> getDepartureAirports();

    @Query("""
            select a from Airport a
            join Flight f on a.code = f.arrivalAirport
            where f is not null
            """)
    List<Airport> getArrivalAirports();

    @Query("""
            select a from Airport a
            where a.city like '%' || :city || '%'
            """)
    List<Airport> findAirportByCityLike(String city);
}
