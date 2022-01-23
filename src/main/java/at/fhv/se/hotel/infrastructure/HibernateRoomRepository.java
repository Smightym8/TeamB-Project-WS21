package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the implementation of the interface {@link RoomRepository}.
 * It provides the functionality to
 * get all rooms
 * get all free rooms
 * get a room by name
 * get a room by category and status
 * save a room in the database
 */
@Component
public class HibernateRoomRepository implements RoomRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all rooms from the database
     * @return a list with all rooms
     */
    @Override
    public List<Room> findAllRooms() {
        TypedQuery<Room> query = this.em.createQuery("SELECT r FROM Room r", Room.class);
        return query.getResultList();
    }

    /**
     * This method gets all free rooms from the database
     * @return a list with all free rooms
     */
    @Override
    public List<Room> findAllFreeRooms() {
        TypedQuery<Room> query = this.em.createQuery(
                "FROM Room AS r WHERE r.status = :status", Room.class);
        query.setParameter("status", RoomStatus.FREE);

        return query.getResultList();
    }

    /**
     * This method gets a room by name from the database
     * @param name contains the name of the room
     * @return the room with the provided name
     */
    @Override
    public Optional<Room> roomByName(RoomName name) {
        TypedQuery<Room> query = this.em.createQuery("FROM Room AS r WHERE r.roomName = :name", Room.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    /**
     * This method gets a room by category and status from the database
     * @param categoryId contains the id of the category
     * @param status contains the status of the room
     * @return the room with the provided category and status
     */
    @Override
    public List<Room> roomsByCategoryAndStatus(RoomCategoryId categoryId, RoomStatus status) {
        TypedQuery<Room> query = this.em.createQuery(
                "FROM Room AS r WHERE r.roomCategory.roomCategoryId = :roomCategoryId AND r.status = :status", Room.class);

        query.setParameter("roomCategoryId", categoryId);
        query.setParameter("status", status);

        return query.getResultList();
    }

    /**
     * This method saves a room in the database
     * @param room contains the room which will be saved
     */
    @Override
    public void add(Room room) {
        this.em.persist(room);
    }
}
