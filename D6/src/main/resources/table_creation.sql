DROP TABLE if exists pricings4;

CREATE TABLE bookings.pricings AS
SELECT f.flight_no,
       tf.fare_conditions,
       tf.amount
FROM bookings.flights f
         JOIN bookings.ticket_flights tf
              ON f.flight_id = tf.flight_id
         JOIN bookings.boarding_passes bp
              ON tf.flight_id = bp.flight_id
                  AND tf.ticket_no = bp.ticket_no
GROUP BY f.flight_no, tf.fare_conditions, tf.amount
ORDER BY f.flight_no