package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HibernateGuestRepository implements GuestRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Guest> findAllGuests() {
        TypedQuery<Guest> query = this.em.createQuery("SELECT g from Guest g", Guest.class);
        return query.getResultList();
    }

    @Override
    public GuestId nextIdentity() {
        return new GuestId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Guest guest) {
        this.em.persist(guest);
    }

    @Override
    public void remove(Guest guest) {
        this.em.remove(guest);
    }

    @Override
    public Optional<Guest> guestById(GuestId guestId) {
        TypedQuery<Guest> query = this.em.createQuery("FROM Guest AS g WHERE g.guestId = :guestId", Guest.class);
        query.setParameter("guestId", guestId);
        return singleResultOptional(query);
    }

    private static <T> Optional<T> singleResultOptional(TypedQuery<T> query) {
        // NOTE: getSingleResult throws an error if there is none
        List<T> result = query.getResultList();
        if (1 != result.size()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }
}
