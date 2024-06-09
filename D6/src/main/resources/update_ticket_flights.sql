UPDATE bookings.ticket_flights as tf
SET fare_conditions='Economy+' FROM bookings.seats as s
JOIN bookings.boarding_passes as bp
ON s.seat_no=bp.seat_no
    JOIN bookings.flights as f
    ON f.flight_id=bp.flight_id AND f.aircraft_code=s.aircraft_code
WHERE tf.ticket_no=bp.ticket_no
  AND tf.flight_id=bp.flight_id
  AND s.fare_conditions='Economy+'