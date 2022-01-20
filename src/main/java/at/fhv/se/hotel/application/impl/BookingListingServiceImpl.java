package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.dto.BookingListingDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link BookingListingService}.
 * It provides the functionality to get all bookings.
 */
@Component
public class BookingListingServiceImpl implements BookingListingService {

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * This method provides all bookings.
     * @return a list of BookingListingDTO objects.
     */
    @Transactional(readOnly=true)
    @Override
    public List<BookingListingDTO> allBookings() {
        List<Booking> bookings = bookingRepository.findAllBookings();
        List<BookingListingDTO> dtos = new ArrayList<>();

        for (Booking b : bookings) {
            BookingListingDTO dto = BookingListingDTO.builder()
                    .withId(b.getBookingId().id())
                    .withBookingNumber(b.getBookingNumber())
                    .withGuestFirstName(b.getGuest().getName().firstName())
                    .withGuestLastName(b.getGuest().getName().lastName())
                    .withStreetName(b.getGuest().getAddress().streetName())
                    .withStreetNumber(b.getGuest().getAddress().streetNumber())
                    .withZipCode(b.getGuest().getAddress().zipCode())
                    .withCity(b.getGuest().getAddress().city())
                    .withCheckInDate(b.getCheckInDate())
                    .withCheckOutDate(b.getCheckOutDate())
                    .withStatus(b.isActive())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
