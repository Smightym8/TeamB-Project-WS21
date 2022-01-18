package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.infrastructure.HibernateGuestRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get guest objects from the database.
 * The implementation is in {@link HibernateGuestRepository}
 */
public interface GuestRepository {

    /**
     * See implementation
     * {@link HibernateGuestRepository#findAllGuests()}
     */
    List<Guest> findAllGuests();

    /**
     * See implementation
     * {@link HibernateGuestRepository#nextIdentity()}
     */
    GuestId nextIdentity();

    /**
     * See implementation
     * {@link HibernateGuestRepository#add(Guest)}
     */
    void add(Guest guest);

    /**
     * See implementation
     * {@link HibernateGuestRepository#guestById(GuestId)}
     */
    Optional<Guest> guestById(GuestId guestId);
}
