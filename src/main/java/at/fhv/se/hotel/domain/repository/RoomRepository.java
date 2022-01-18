package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.infrastructure.HibernateRoomRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get room objects from the database.
 * The implementation is in {@link HibernateRoomRepository}
 */
public interface RoomRepository {

    /**
     * See implementation
     * {@link HibernateRoomRepository#findAllRooms()}
     */
    List<Room> findAllRooms();

    /**
     * See implementation
     * {@link HibernateRoomRepository#findAllFreeRooms()} ()}
     */
    List<Room> findAllFreeRooms();

    /**
     * See implementation
     * {@link HibernateRoomRepository#roomByName(RoomName)}
     */
    Optional<Room> roomByName(RoomName name);

    /**
     * See implementation
     * {@link HibernateRoomRepository#roomsByCategoryAndStatus(RoomCategoryId, RoomStatus)}
     */
    List<Room> roomsByCategoryAndStatus(RoomCategoryId categoryId, RoomStatus status);

    /**
     * See implementation
     * {@link HibernateRoomRepository#add(Room)}
     */
    void add(Room room);
}
