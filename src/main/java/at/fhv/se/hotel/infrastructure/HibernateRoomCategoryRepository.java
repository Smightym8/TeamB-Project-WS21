package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class HibernateRoomCategoryRepository implements RoomCategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RoomCategory> findAllRoomCategories() {
        TypedQuery<RoomCategory> query = this.em.createQuery("SELECT rc from RoomCategory rc", RoomCategory.class);
        return query.getResultList();
    }

    @Override
    public Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId) {
        TypedQuery<RoomCategory> query = this.em.createQuery(
                "FROM RoomCategory AS rc WHERE rc.roomCategoryId = :roomCategoryId", RoomCategory.class);
        query.setParameter("roomCategoryId", roomCategoryId);
        return singleResultOptional(query);
    }

    private static <T> Optional<T> singleResultOptional(TypedQuery<T> query) {
        // NOTE: getSingleResult throws an error if there is none
        List<T> result = query.getResultList();
        if (1 != result.size()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }
}
