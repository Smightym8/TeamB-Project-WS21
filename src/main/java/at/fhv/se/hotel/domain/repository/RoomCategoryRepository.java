package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.infrastructure.HibernateRoomCategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get invoice objects from the database.
 * The implementation is in {@link HibernateRoomCategoryRepository}
 */
public interface RoomCategoryRepository {

    /**
     * See implementation
     * {@link HibernateRoomCategoryRepository#findAllRoomCategories()}
     */
    List<RoomCategory> findAllRoomCategories();

    /**
     * See implementation
     * {@link HibernateRoomCategoryRepository#nextIdentity()}
     */
    RoomCategoryId nextIdentity();

    /**
     * See implementation
     * {@link HibernateRoomCategoryRepository#add(RoomCategory)}
     */
    void add(RoomCategory roomCategory);

    /**
     * See implementation
     * {@link HibernateRoomCategoryRepository#roomCategoryById(RoomCategoryId)}
     */
    Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId);

    /**
     * See implementation
     * {@link HibernateRoomCategoryRepository#roomCategoryByName(RoomCategoryName)}
     */
    Optional<RoomCategory> roomCategoryByName(RoomCategoryName roomCategoryName);
}
