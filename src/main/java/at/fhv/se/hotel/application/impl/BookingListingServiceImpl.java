package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.dto.BookingDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link BookingListingService}.
 * It provides the functionality to get all bookings.
 */
@Component
public class BookingListingServiceImpl implements BookingListingService {

    /**
     * This method provides bookings.
     * @return bookings contains the booking objects.
     */
    @Override
    public List<BookingDTO> allBookings() {
        // TODO: this is fake test data, remove when implementing
        final List<BookingDTO> bookings = Arrays.asList(
                BookingDTO.builder()
                        .withId("1")
                        .withRoomCategory("Single Room")
                        .withCheckInDate(LocalDate.now())
                        .withCheckOutDate(LocalDate.now().plusDays(10))
                        .build(),
                BookingDTO.builder()
                        .withId("2")
                        .withRoomCategory("Junior Suite")
                        .withCheckInDate(LocalDate.now().plusDays(2))
                        .withCheckOutDate(LocalDate.now().plusDays(8))
                        .build(),
                BookingDTO.builder()
                        .withId("3")
                        .withRoomCategory("Suite")
                        .withCheckInDate(LocalDate.now().plusDays(30))
                        .withCheckOutDate(LocalDate.now().plusDays(40))
                        .build()
        );

        return bookings;
    }
}
