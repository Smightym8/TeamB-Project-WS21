package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.dto.BookingDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link BookingListingService}.
 * It provides the functionality to get all bookings.
 */
@Component
public class BookingListingServiceImpl implements BookingListingService {

    @Autowired
    BookingRepository bookingRepository;

    /**
     * This method provides bookings.
     * @return bookings contains the booking objects.
     */
    @Transactional(readOnly=true)
    @Override
    public List<BookingDTO> allBookings() {
        List<Booking> bookings = bookingRepository.findAllBookings();
        List<BookingDTO> dtos = new ArrayList<>();

        for (Booking b : bookings) {
            BookingDTO dto = BookingDTO.builder()
                    .withId(b.getBookingId().id())
                    .withGuestName(b.getGuest().getName().firstName() + " " + b.getGuest().getName().lastName())
                    .withBirthDate(b.getGuest().getBirthDate())
                    .withCheckInDate(b.getCheckInDate())
                    .withCheckOutDate(b.getCheckOutDate())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
