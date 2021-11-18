package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateRoomRepository implements RoomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Room> roomByName(String name) {
        TypedQuery<Room> query = this.em.createQuery("FROM Room AS r WHERE r.name = :name", Room.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Room> roomsByCategoryAndStatus(RoomCategoryId categoryId, RoomStatus status) {
        TypedQuery<Room> query = this.em.createQuery(
                "FROM Room AS r WHERE r.roomCategory.roomCategoryId = :roomCategoryId AND r.status = :status", Room.class);

        query.setParameter("roomCategoryId", categoryId);
        query.setParameter("status", status);

        return query.getResultList();
    }

    @Override
    public void add(Room room) {
        this.em.persist(room);
        this.em.flush();
    }

    @Override
    public void occupyRoom(Room room) {
        room.occupy();
        em.merge(room);
    }
}