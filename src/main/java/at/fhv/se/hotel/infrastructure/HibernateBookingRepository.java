package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.repository.BookingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class HibernateBookingRepository implements BookingRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Booking> findAllBookings() {
        return null;
    }

    @Override
    public BookingId nextIdentity() {
        return null;
    }

    @Override
    public void add(Booking booking) {

    }

    @Override
    public Optional<Booking> bookingById(BookingId bookingId) {
        return Optional.empty();
    }
}
