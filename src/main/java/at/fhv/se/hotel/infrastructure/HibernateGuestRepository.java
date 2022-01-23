package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link GuestRepository}.
 * It provides the functionality to
 * get all guests
 * generate a new guest id
 * save a guest in the database
 * get a guest by id
 */
@Component
public class HibernateGuestRepository implements GuestRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all guests from the database
     * @return a list with all guests
     */
    @Override
    public List<Guest> findAllGuests() {
        TypedQuery<Guest> query = this.em.createQuery("SELECT g from Guest g", Guest.class);
        return query.getResultList();
    }

    /**
     * This method generates a new guest id
     * @return the generated id
     */
    @Override
    public GuestId nextIdentity() {
        return new GuestId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a guest in the database
     * @param guest contains the guest
     */
    @Override
    public void add(Guest guest) {
        this.em.persist(guest);
    }

    /**
     * This method gets a guest with a specific id from the database
     * @param guestId contains the id of the guest
     * @return the guest with the provided id
     */
    @Override
    public Optional<Guest> guestById(GuestId guestId) {
        TypedQuery<Guest> query = this.em.createQuery("FROM Guest AS g WHERE g.guestId = :guestId", Guest.class);
        query.setParameter("guestId", guestId);
        return query.getResultStream().findFirst();
    }
}
