package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPriceId;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.infrastructure.HibernateRoomCategoryPriceRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get RoomCategoryPrice objects from the database.
 * The implementation is in {@link HibernateRoomCategoryPriceRepository}
 */
public interface RoomCategoryPriceRepository {

    /**
     * See implementation
     * {@link HibernateRoomCategoryPriceRepository#nextIdentity()}
     */
    RoomCategoryPriceId nextIdentity();

    /**
     * See implementation
     * {@link HibernateRoomCategoryPriceRepository#add(RoomCategoryPrice)}
     */
    void add(RoomCategoryPrice roomCategoryPrice);

    /**
     * See implementation
     * {@link HibernateRoomCategoryPriceRepository#priceBySeasonAndCategory(SeasonId, RoomCategoryId)}
     */
    Optional<RoomCategoryPrice> priceBySeasonAndCategory(SeasonId seasonId, RoomCategoryId roomCategoryId);

    /**
     * See implementation
     * {@link HibernateRoomCategoryPriceRepository#allPrices()}
     */
    List<RoomCategoryPrice> allPrices();
}
