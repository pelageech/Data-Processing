package com.example.dps_d.service;

import com.example.dps_d.dto.BookingsResponseDto;
import com.example.dps_d.dto.CheckInDto;
import com.example.dps_d.dto.ContactData;
import com.example.dps_d.dto.CreateBookingDto;
import com.example.dps_d.dto.InboundFlightDto;
import com.example.dps_d.dto.OutboundFlightDto;
import com.example.dps_d.dto.RoutesDto;
import com.example.dps_d.entity.Airport;
import com.example.dps_d.entity.BoardingPass;
import com.example.dps_d.entity.Booking;
import com.example.dps_d.entity.Flight;
import com.example.dps_d.entity.Ticket;
import com.example.dps_d.entity.TicketFlight;
import com.example.dps_d.exception.ClientException;
import com.example.dps_d.repository.AircraftRepository;
import com.example.dps_d.repository.AirportRepository;
import com.example.dps_d.repository.BoardingPassRepository;
import com.example.dps_d.repository.BookingRepository;
import com.example.dps_d.repository.FlightRepository;
import com.example.dps_d.repository.SeatRepository;
import com.example.dps_d.repository.TicketFlightRepository;
import com.example.dps_d.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final BoardingPassRepository boardingPassRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final TicketFlightRepository ticketFlightRepository;
    private final TicketRepository ticketRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public List<String> getDepartureCities(String language) {
        return switch (language.toLowerCase()) {
            case "en", "english" -> airportRepository.getDepartureCities().stream().map(v -> v.split(";")[1]).toList();
            case "ru", "russian" -> airportRepository.getDepartureCities().stream().map(v -> v.split(";")[0]).toList();
            default -> airportRepository.getDepartureCities();
        };
    }

    @Transactional(readOnly = true)
    public List<String> getArrivalCities(String language) {
        return switch (language.toLowerCase()) {
            case "en", "english" -> airportRepository.getArrivalCities().stream().map(v -> v.split(";")[1]).toList();
            case "ru", "russian" -> airportRepository.getArrivalCities().stream().map(v -> v.split(";")[0]).toList();
            default -> airportRepository.getArrivalCities();
        };
    }

    @Transactional(readOnly = true)
    public List<Airport> getDepartureAirports(String language) {
        return switch (language.toLowerCase()) {
            case "en", "english" -> airportRepository.getDepartureAirports().stream()
                    .map(v -> v.setCity(v.getCity().split(";")[1])
                            .setName(v.getName().split(";")[1]))
                    .toList();
            case "ru", "russian" -> airportRepository.getDepartureAirports().stream()
                    .map(v -> v.setCity(v.getCity().split(";")[0])
                            .setName(v.getName().split(";")[0]))
                    .toList();
            default -> airportRepository.getDepartureAirports();
        };
    }

    @Transactional(readOnly = true)
    public List<Airport> getArrivalAirports(String language) {
        return switch (language.toLowerCase()) {
            case "en", "english" -> airportRepository.getArrivalAirports().stream()
                    .map(v -> v.setCity(v.getCity().split(";")[1])
                            .setName(v.getName().split(";")[1]))
                    .toList();
            case "ru", "russian" -> airportRepository.getArrivalAirports().stream()
                    .map(v -> v.setCity(v.getCity().split(";")[0])
                            .setName(v.getName().split(";")[0]))
                    .toList();
            default -> airportRepository.getArrivalAirports();
        };
    }

    @Transactional(readOnly = true)
    public List<Airport> getAirports(String city) {
        return airportRepository.findAirportByCityLike(city);
    }

    @Transactional(readOnly = true)
    public List<InboundFlightDto> getInboundFlights(String airport) {
        return flightRepository.findInboundFlightsByAirportName(airport)
                .stream()
                .map(v -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(v.getScheduledArrival());
                            return new InboundFlightDto(
                                    DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK)),
                                    v.getScheduledArrival(),
                                    v.getFlightNo(),
                                    v.getDepartureAirport());
                        }
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<OutboundFlightDto> getOutboundFlights(String airport) {
        return flightRepository.findOutboundFlightsByAirportName(airport)
                .stream()
                .map(v -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(v.getScheduledArrival());
                            return new OutboundFlightDto(
                                    DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK)),
                                    v.getScheduledArrival(),
                                    v.getFlightNo(),
                                    v.getArrivalAirport());
                        }
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<RoutesDto> getRoutes(String point1, String point2, LocalDateTime dateBefore, String fareConditions, Integer conn) {
        LocalDateTime dateAfter = dateBefore.plusDays(1);
        switch (conn) {
            case 1:
                return Stream.of(
                                flightRepository.getRoutes1(point1, point2, dateBefore, dateAfter))
                        .flatMap(Collection::stream)
                        .map(route -> {
                            List<Flight> flights = Stream.of(route.getFlight4())
                                    .filter(Objects::nonNull)
                                    .toList();

                            Float price = flights.stream()
                                    .map(v -> ticketFlightRepository.findTicketFlightByFlightNoAndFare(v.getFlightNo(), fareConditions))
                                    .filter(Objects::nonNull)
                                    .reduce(Float::sum)
                                    .orElse(null);

                            return new RoutesDto()
                                    .setConnectionNum(route.getConnectionNum())
                                    .setRoute(route.getRoute())
                                    .setFlights(
                                            Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                                                    .filter(Objects::nonNull)
                                                    .toList())
                                    .setPrice(price);
                        })
                        .toList();
            case 2:
                return Stream.of(
                                flightRepository.getRoutes2(point1, point2, dateBefore, dateAfter))
                        .flatMap(Collection::stream)
                        .map(route -> {
                            List<Flight> flights = Stream.of(route.getFlight2())
                                    .filter(Objects::nonNull)
                                    .toList();

                            Float price = flights.stream()
                                    .map(v -> ticketFlightRepository.findTicketFlightByFlightNoAndFare(v.getFlightNo(), fareConditions))
                                    .filter(Objects::nonNull)
                                    .reduce(Float::sum)
                                    .orElse(null);

                            return new RoutesDto()
                                    .setConnectionNum(route.getConnectionNum())
                                    .setRoute(route.getRoute())
                                    .setFlights(
                                            Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                                                    .filter(Objects::nonNull)
                                                    .toList())
                                    .setPrice(price);
                        })
                        .toList();
            case 3:
                return Stream.of(
                                flightRepository.getRoutes3(point1, point2, dateBefore, dateAfter))
                        .flatMap(Collection::stream)
                        .map(route -> {
                            List<Flight> flights = Stream.of(route.getFlight3())
                                    .filter(Objects::nonNull)
                                    .toList();

                            Float price = flights.stream()
                                    .map(v -> ticketFlightRepository.findTicketFlightByFlightNoAndFare(v.getFlightNo(), fareConditions))
                                    .filter(Objects::nonNull)
                                    .reduce(Float::sum)
                                    .orElse(null);

                            return new RoutesDto()
                                    .setConnectionNum(route.getConnectionNum())
                                    .setRoute(route.getRoute())
                                    .setFlights(
                                            Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                                                    .filter(Objects::nonNull)
                                                    .toList())
                                    .setPrice(price);
                        })
                        .toList();
            case 4:
                return Stream.of(
                                flightRepository.getRoutes4(point1, point2, dateBefore, dateAfter))
                        .flatMap(Collection::stream)
                        .map(route -> {
                            List<Flight> flights = Stream.of(route.getFlight4())
                                    .filter(Objects::nonNull)
                                    .toList();

                            Float price = flights.stream()
                                    .map(v -> ticketFlightRepository.findTicketFlightByFlightNoAndFare(v.getFlightNo(), fareConditions))
                                    .filter(Objects::nonNull)
                                    .reduce(Float::sum)
                                    .orElse(null);

                            return new RoutesDto()
                                    .setConnectionNum(route.getConnectionNum())
                                    .setRoute(route.getRoute())
                                    .setFlights(
                                            Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                                                    .filter(Objects::nonNull)
                                                    .toList())
                                    .setPrice(price);
                        })
                        .toList();
        }
        return Stream.of(
                        flightRepository.getRoutes1(point1, point2, dateBefore, dateAfter),
                        flightRepository.getRoutes2(point1, point2, dateBefore, dateAfter),
                        flightRepository.getRoutes3(point1, point2, dateBefore, dateAfter),
                        flightRepository.getRoutes4(point1, point2, dateBefore, dateAfter))
                .flatMap(Collection::stream)
                .map(route -> {
                    List<Flight> flights = Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                            .filter(Objects::nonNull)
                            .toList();

                    Float price = flights.stream()
                            .map(v -> ticketFlightRepository.findTicketFlightByFlightNoAndFare(v.getFlightNo(), fareConditions))
                            .filter(Objects::nonNull)
                            .reduce(Float::sum)
                            .orElse(null);

                    return new RoutesDto()
                            .setConnectionNum(route.getConnectionNum())
                            .setRoute(route.getRoute())
                            .setFlights(
                                    Stream.of(route.getFlight1(), route.getFlight2(), route.getFlight3(), route.getFlight4())
                                            .filter(Objects::nonNull)
                                            .toList())
                            .setPrice(price);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public Float predictCost(String departureAirport, String arrivalAirport, String aircraftCode, String fareConditions) {
        return ticketFlightRepository.predictCost(departureAirport, arrivalAirport, aircraftCode, fareConditions);
    }

    @Transactional
    public BookingsResponseDto createBooking(CreateBookingDto bookingDto) {
        Date date = Calendar.getInstance().getTime();

        List<Flight> flights = Optional.of(bookingDto)
                .map(CreateBookingDto::getFlights)
                .stream()
                .flatMap(Collection::stream)
                .map(flightRepository::findFlightById)
                .toList();

        validateFlight(flights, bookingDto.getFlights());

        Map<Long, Float> prices = flights.stream()
                .collect(Collectors.toMap(
                        Flight::getId,
                        v -> Optional.ofNullable(ticketFlightRepository.predictCost(
                                        v.getDepartureAirport(),
                                        v.getArrivalAirport(),
                                        v.getAircraftCode(),
                                        bookingDto.getFareConditions()))
                                .orElse(0F)
                ));
        Float totalPrice = prices.values()
                .stream().reduce(0F, Float::sum);

        if (prices.values().stream().anyMatch(v -> v.equals(0F))) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Полет не найден");
        }

        List<Long> flightIds = flights.stream().map(Flight::getId).toList();

        Booking existingBooking = bookingRepository.findBooking(flightIds);

        if (existingBooking != null) {
            List<TicketFlight> ticketFlights = ticketFlightRepository.findByBookRef(existingBooking.getBookRef());

            return new BookingsResponseDto()
                    .setTicketFlights(ticketFlights)
                    .setBookRef(existingBooking.getBookRef());
        }

        String bookRef = "_" + UUID.randomUUID().toString().substring(0, 5);
        Booking booking = new Booking(bookRef, date, totalPrice);
        bookingRepository.save(booking);

        var ticketFlights = bookingDto.getFlights().stream()
                .map(flight -> {
                            TicketFlight existedTicketFlight = flightRepository
                                    .findByPassengerId(bookingDto.getPassengerId(), flight);

                            if (existedTicketFlight != null) {
                                return existedTicketFlight;
                            }

                            String ticketNo = "_" + UUID.randomUUID().toString().substring(0, 5);


                            Ticket ticket = new Ticket(
                                    ticketNo,
                                    bookRef,
                                    bookingDto.getPassengerId(),
                                    bookingDto.getPassengerName(),
                                    new ContactData(bookingDto.getPhone(), bookingDto.getEmail())
                            );

                            ticketRepository.save(ticket);

                            TicketFlight ticketFlight = new TicketFlight(
                                    ticketNo,
                                    flight,
                                    bookingDto.getFareConditions(),
                                    prices.get(flight));
                            ticketFlightRepository.save(ticketFlight);

                            return ticketFlight;
                        }
                )
                .toList();
        return new BookingsResponseDto()
                .setBookRef(bookRef)
                .setTicketFlights(ticketFlights);
    }

    public String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private void validateFlight(List<Flight> flights, List<Long> flightData) {
        if (flightData.size() != flights.size()) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Полет не найден");
        }

//        flights.forEach(f -> {
//            switch (f.getStatus()) {
//                case "Arrived", "On Time" ->
//                        throw ClientException.of(HttpStatus.BAD_REQUEST, "Регистрация на полет закрыта");
//            }
//        });

    }

    @Transactional
    public List<BoardingPass> createCheckIn(CheckInDto checkInDto) {
        List<TicketFlight> ticketFlights = ticketFlightRepository.findByBookRef(checkInDto.getBookRef());

        List<BoardingPass> existingPasses = boardingPassRepository.findExistingPasses(checkInDto.getBookRef());
        if (!existingPasses.isEmpty()) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Регистрация уже прошла");
        }

        return ticketFlights.stream().map(flight -> {


                    List<BoardingPass> passes = boardingPassRepository.findAllByFlightIdAndFareConditions(
                            flight.getFlightId());

                    List<String> freeSeats = boardingPassRepository.findFreeSeatsByFlightIdAndFareConditions(
                            flight.getFlightId(), flight.getFareConditions());

                    if (freeSeats.isEmpty()) {
                        throw ClientException.of(HttpStatus.BAD_REQUEST, "Нет свободных мест");
                    }

                    String seatNo = freeSeats.get(0);

//                    String seatNo = "AAA";

                    long boardingNo = passes.isEmpty()
                            ? 1
                            : passes.get(passes.size() - 1).getBoardingNo() + 1;

                    BoardingPass boardingPass = new BoardingPass(flight.getTicketNo(),
                            flight.getFlightId(), boardingNo, seatNo);
                    return boardingPassRepository.save(boardingPass);
                }
        ).toList();
    }
}
