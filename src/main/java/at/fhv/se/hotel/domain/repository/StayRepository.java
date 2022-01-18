package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.infrastructure.HibernateRoomCategoryRepository;
import at.fhv.se.hotel.infrastructure.HibernateStayRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get stay objects from the database.
 * The implementation is in {@link at.fhv.se.hotel.infrastructure.HibernateStayRepository}
 */
public interface StayRepository {

    /**
     * See implementation
     * {@link HibernateStayRepository#findAllStays()}
     */
    List<Stay> findAllStays();

    /**
     * See implementation
     * {@link HibernateStayRepository#nextIdentity()}
     */
    StayId nextIdentity();

    /**
     * See implementation
     * {@link HibernateStayRepository#add(Stay)}
     */
    void add(Stay stay);

    /**
     * See implementation
     * {@link HibernateStayRepository#stayById(StayId)}
     */
    Optional<Stay> stayById(StayId stayId);
}
