package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateRoomCategoryPriceRepository implements RoomCategoryPriceRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public RoomCategoryPriceId nextIdentity() {
        return new RoomCategoryPriceId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(RoomCategoryPrice roomCategoryPrice) {
        this.em.persist(roomCategoryPrice);
    }

    @Override
    public Optional<RoomCategoryPrice> priceBySeasonAndCategory(SeasonId seasonId, RoomCategoryId roomCategoryId) {
        TypedQuery<RoomCategoryPrice> query = this.em.createQuery(
                "FROM RoomCategoryPrice AS rcp WHERE rcp.season.seasonId = :seasonId " +
                        "AND rcp.roomCategory.roomCategoryId = :roomCategoryId",
                RoomCategoryPrice.class
        );

        query.setParameter("seasonId", seasonId);
        query.setParameter("roomCategoryId", roomCategoryId);

        return query.getResultStream().findFirst();
    }

    // TODO: Test!
    @Override
    public List<RoomCategoryPrice> allPrices() {
        TypedQuery<RoomCategoryPrice> query = this.em.createQuery("SELECT rcp FROM RoomCategoryPrice rcp", RoomCategoryPrice.class);
        return query.getResultList();
    }
}
