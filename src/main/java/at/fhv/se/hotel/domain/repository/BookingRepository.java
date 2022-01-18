package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.infrastructure.HibernateBookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get booking objects from the database.
 * The implementation is in {@link HibernateBookingRepository}
 */
public interface BookingRepository {
    /**
     * See implementation
     * {@link HibernateBookingRepository#findAllBookings()}
     */
    List<Booking> findAllBookings();

    /**
     * See implementation
     * {@link HibernateBookingRepository#nextIdentity()}
     */
    BookingId nextIdentity();

    /**
     * See implementation
     * {@link HibernateBookingRepository#add(Booking)}
     */
    void add(Booking booking);

    /**
     * See implementation
     * {@link HibernateBookingRepository#bookingById(BookingId)}
     */
    Optional<Booking> bookingById(BookingId bookingId);

    /**
     * See implementation
     * {@link HibernateBookingRepository#amountOfBookingsByDate(LocalDate)}
     */
    int amountOfBookingsByDate(LocalDate bookingDate);
}
