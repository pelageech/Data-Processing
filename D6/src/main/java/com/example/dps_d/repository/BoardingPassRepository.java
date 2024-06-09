package com.example.dps_d.repository;

import com.example.dps_d.entity.BoardingPass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardingPassRepository extends JpaRepository<BoardingPass, String> {

    @Query("""
            select bp from BoardingPass bp
            where bp.flightId = :flightId
            """)
    List<BoardingPass> findAllByFlightIdAndFareConditions(Long flightId);

    @Query("""
            select s.seatNo from Seat s
            join Flight f on f.id = :flightId
            where s.aircraftCode = f.aircraftCode and s.fareConditions = :fareConditions
                        
            except
                        
            select bp.seatNo from BoardingPass bp
            where bp.flightId = :flightId
            """)
    List<String> findFreeSeatsByFlightIdAndFareConditions(Long flightId, String fareConditions);

    @Query("""
            select p from BoardingPass p
            join Ticket t on t.ticketNo = p.ticketNo and t.bookRef = :bookRef
            """)
    List<BoardingPass> findExistingPasses(String bookRef);
}
