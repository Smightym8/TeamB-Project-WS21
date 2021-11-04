package at.fhv.se.hotel.domain.repositories;

import java.util.Optional;

public interface BookingRepository {
    List<Booking> findAllBookings();

    BookingId nextIdentity();

    void add(Booking booking);

    Optional<Booking> bookingById(BookingId bookingId);
}
