select f1.flight_id, tf.fare_conditions, avg(tf.amount), to_char(f1.scheduled_arrival, 'DAY') as day
from ticket_flights tf
     join flights f1
          on f1.flight_id = tf.flight_id
              and f1.aircraft_code = :aircraftCode
              and f1.departure_airport = :departureAirport
              and f1.arrival_airport = :arrivalAirport
group by f1.flight_id, tf.fare_conditions