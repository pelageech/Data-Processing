SELECT f.flight_no,
       bp.seat_no,
       tf.fare_conditions,
       tf.amount
FROM bookings.flights f
         JOIN bookings.ticket_flights tf
              ON f.flight_id = tf.flight_id
         JOIN bookings.boarding_passes bp
              ON tf.flight_id = bp.flight_id
                  AND tf.ticket_no = bp.ticket_no
GROUP BY f.flight_no, bp.seat_no, tf.fare_conditions, tf.amount
ORDER BY f.flight_no
--, ticket_flights.ticket_no--, ticket_flights.amount

--154748 and 154768 154751- cheap
--154845 and 154861 154783 - exp