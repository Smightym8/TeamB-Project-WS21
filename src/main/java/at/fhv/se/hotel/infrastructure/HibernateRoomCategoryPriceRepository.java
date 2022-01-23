package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPriceId;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link RoomCategoryPriceRepository}.
 * It provides the functionality to
 * generate a new room category price id
 * save a room category price in the database
 * get a price by season and category
 * get all room category prices
 */
@Component
public class HibernateRoomCategoryPriceRepository implements RoomCategoryPriceRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method generates a new room category price id
     * @return the generated id
     */
    @Override
    public RoomCategoryPriceId nextIdentity() {
        return new RoomCategoryPriceId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a room category price in the database
     * @param roomCategoryPrice contains the room category price which will be saved
     */
    @Override
    public void add(RoomCategoryPrice roomCategoryPrice) {
        this.em.persist(roomCategoryPrice);
    }

    /**
     * This method gets a room category price by season and room category from the database
     * @param seasonId contains the id of the season
     * @param roomCategoryId contains the id of the room category
     * @return the price for the provided room category and season
     */
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

    /**
     * This method gets all room category prices from the database
     * @return a list with all room category prices
     */
    @Override
    public List<RoomCategoryPrice> allPrices() {
        TypedQuery<RoomCategoryPrice> query = this.em.createQuery("SELECT rcp FROM RoomCategoryPrice rcp", RoomCategoryPrice.class);
        return query.getResultList();
    }
}
