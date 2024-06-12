package com.example.dps_d.repository;

import com.example.dps_d.entity.Booking;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("""
            select b from Booking b
            join Ticket t on t.bookRef = b.bookRef
            join TicketFlight tf on tf.ticketNo = t.ticketNo and t.bookRef = b.bookRef
            and tf.flightId in :flightIds
            where tf is not null
            """)
    Booking findBooking(List<Long> flightIds);
}
