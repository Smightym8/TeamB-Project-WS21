package at.fhv.se.hotel.domain.repositories;

import at.fhv.se.hotel.domain.models.Booking;
import at.fhv.se.hotel.domain.models.BookingId;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    List<Booking> findAllBookings();

    BookingId nextIdentity();

    void add(Booking booking);

    Optional<Booking> bookingById(BookingId bookingId);
}
