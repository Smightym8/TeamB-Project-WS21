package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class HibernateRoomRepository implements RoomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Room> roomsByCategoryAndStatus(String categoryId, RoomStatus status) {
        TypedQuery<Room> query = this.em.createQuery("FROM Room AS r WHERE r.roomCategory = :roomCategory AND " +
                "r.roomStatus := status", Room.class);

        query.setParameter("roomCategory", categoryId);
        query.setParameter("status", status);

        return query.getResultList();
    }

    @Override
    public void add(Room room) {
        this.em.persist(room);
        this.em.flush();
    }
}
