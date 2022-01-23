package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link RoomCategoryRepository}.
 * It provides the functionality to
 * get all room categories
 * generate a new room category id
 * save a room category in the database
 * get a room category by id
 * get a room category by name
 */
@Component
public class HibernateRoomCategoryRepository implements RoomCategoryRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all room categories from the database
     * @return a list with all room categories
     */
    @Override
    public List<RoomCategory> findAllRoomCategories() {
        TypedQuery<RoomCategory> query = this.em.createQuery("SELECT rc from RoomCategory rc", RoomCategory.class);
        return query.getResultList();
    }

    /**
     * This method generates a new room category id
     * @return the generated id
     */
    @Override
    public RoomCategoryId nextIdentity() {
        return new RoomCategoryId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a room category in the database
     * @param roomCategory contains the room category that will be saved
     */
    @Override
    public void add(RoomCategory roomCategory) {
        this.em.persist(roomCategory);
    }

    /**
     * This method gets a room category by id from the database
     * @param roomCategoryId contains the id of the room category
     * @return the room category with the provided id
     */
    @Override
    public Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId) {
        TypedQuery<RoomCategory> query = this.em.createQuery(
                "FROM RoomCategory AS rc WHERE rc.roomCategoryId = :roomCategoryId", RoomCategory.class);
        query.setParameter("roomCategoryId", roomCategoryId);
        return query.getResultStream().findFirst();
    }

    /**
     * This method gets a room category by name from the database
     * @param roomCategoryName contains the name of the room category
     * @return the room category with the provided name
     */
    @Override
    public Optional<RoomCategory> roomCategoryByName(RoomCategoryName roomCategoryName) {
        TypedQuery<RoomCategory> query = this.em.createQuery(
                "FROM RoomCategory AS rc WHERE rc.roomCategoryName = :roomCategoryName", RoomCategory.class);
        query.setParameter("roomCategoryName", roomCategoryName);
        return query.getResultStream().findFirst();
    }

}
