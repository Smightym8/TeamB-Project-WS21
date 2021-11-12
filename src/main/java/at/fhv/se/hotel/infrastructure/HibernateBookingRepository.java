package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateBookingRepository implements BookingRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Booking> findAllBookings() {
        TypedQuery<Booking> query = this.em.createQuery("SELECT b FROM Booking b", Booking.class);
        return query.getResultList();
    }

    @Override
    public BookingId nextIdentity() {
        return new BookingId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Booking booking) {
        this.em.persist(booking);
    }

    @Override
    public Optional<Booking> bookingById(BookingId bookingId) {
        TypedQuery<Booking> query = this.em.createQuery("FROM Booking AS b WHERE b.bookingId = :bookingId", Booking.class);
        query.setParameter("bookingId", bookingId);
        return query.getResultStream().findFirst();
    }

}
