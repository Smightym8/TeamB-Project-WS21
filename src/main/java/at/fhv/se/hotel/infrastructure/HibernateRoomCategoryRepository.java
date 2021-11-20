package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateRoomCategoryRepository implements RoomCategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RoomCategory> findAllRoomCategories() {
        TypedQuery<RoomCategory> query = this.em.createQuery("SELECT rc from RoomCategory rc", RoomCategory.class);
        return query.getResultList();
    }

    @Override
    public RoomCategoryId nextIdentity() {
        return new RoomCategoryId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(RoomCategory roomCategory) {
        this.em.persist(roomCategory);
    }

    @Override
    public Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId) {
        TypedQuery<RoomCategory> query = this.em.createQuery(
                "FROM RoomCategory AS rc WHERE rc.roomCategoryId = :roomCategoryId", RoomCategory.class);
        query.setParameter("roomCategoryId", roomCategoryId);
        return query.getResultStream().findFirst();
    }

}
