package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    List<Booking> findAllBookings();

    BookingId nextIdentity();

    void add(Booking booking);

    Optional<Booking> bookingById(BookingId bookingId);
}
