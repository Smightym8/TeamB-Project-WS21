package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link BookingRepository}.
 * It provides the functionality to
 * get all bookings
 * generate a new booking id
 * save a booking in the database
 * get a booking by id
 * get the amount of bookings for a specific date
 */
@Component
public class HibernateBookingRepository implements BookingRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all bookings from the database
     * @return the result list which contains all bookings
     */
    @Override
    public List<Booking> findAllBookings() {
        TypedQuery<Booking> query = this.em.createQuery("SELECT b FROM Booking b", Booking.class);
        return query.getResultList();
    }

    /**
     * This method generates a new booking id
     * @return the generated booking id
     */
    @Override
    public BookingId nextIdentity() {
        return new BookingId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a booking in the database
     * @param booking contains the booking which will be saved
     */
    @Override
    public void add(Booking booking) {
        this.em.persist(booking);
    }

    /**
     * This method gets a booking from the database by id
     * @param bookingId contains the id of the booking
     * @return the booking with the provided id
     */
    @Override
    public Optional<Booking> bookingById(BookingId bookingId) {
        TypedQuery<Booking> query = this.em.createQuery("FROM Booking AS b WHERE b.bookingId = :bookingId", Booking.class);
        query.setParameter("bookingId", bookingId);
        return query.getResultStream().findFirst();
    }

    /**
     * This method determines the amount of bookings for a specific date
     * @param bookingDate contains the date when the booking where created
     * @return the size of the result list of bookings for the provided date
     */
    @Override
    public int amountOfBookingsByDate(LocalDate bookingDate) {
        TypedQuery<Booking> query = this.em.createQuery("FROM Booking AS b WHERE b.bookingDate = :bookingDate", Booking.class);
        query.setParameter("bookingDate", bookingDate);
        return query.getResultList().size();
    }

}
