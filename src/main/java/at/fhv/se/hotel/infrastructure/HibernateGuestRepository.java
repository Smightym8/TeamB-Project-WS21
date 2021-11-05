package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class HibernateGuestRepository implements GuestRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Guest> findAllGuests() {
        return null;
    }

    @Override
    public GuestId nextIdentity() {
        return null;
    }

    @Override
    public void add(Guest guest) {

    }

    @Override
    public void remove(Guest guest) {

    }

    @Override
    public Optional<Guest> guestById(GuestId guestId) {
        return Optional.empty();
    }
}
