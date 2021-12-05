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

@Component
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
    public Optional<Guest> guestById(GuestId guestId) {
        TypedQuery<Guest> query = this.em.createQuery("FROM Guest AS g WHERE g.guestId = :guestId", Guest.class);
        query.setParameter("guestId", guestId);
        return query.getResultStream().findFirst();
    }
}
