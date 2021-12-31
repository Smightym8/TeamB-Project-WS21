package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.SeasonCreationService;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class contains the functionality to create and to save a new season.
 */
@Component
public class SeasonCreationServiceImpl implements SeasonCreationService {
    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    /**
     * This method creates a new season and saves the season and the seasonal prices into the database.
     * @param name contains the name of the season.
     * @param startDate contains the start date of the season.
     * @param endDate contains the end date of the season.
     * @param singleRoomPrice contains the Single Room price of the season.
     * @param doubleRoomPrice contains the Double Room price of the season.
     * @param juniorSuitePrice contains the Junior Suite price of the season.
     * @param suitePrice contains the Suite price of the season.
     */
    @Transactional
    @Override
    public void createSeason(String name, LocalDate startDate, LocalDate endDate,
                             BigDecimal singleRoomPrice, BigDecimal doubleRoomPrice,
                             BigDecimal juniorSuitePrice, BigDecimal suitePrice) {

        Season season = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName(name),
                startDate,
                endDate
        );

        seasonRepository.add(season);

        RoomCategoryPrice singleRoomRCP = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                season,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Single Room")).get(),
                singleRoomPrice
        );

        RoomCategoryPrice doubleRoomRCP = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                season,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Double Room")).get(),
                doubleRoomPrice
        );

        RoomCategoryPrice juniorSuiteRCP = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                season,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Junior Suite")).get(),
                juniorSuitePrice
        );

        RoomCategoryPrice suiteRCP = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                season,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Suite")).get(),
                suitePrice
        );

        roomCategoryPriceRepository.add(singleRoomRCP);
        roomCategoryPriceRepository.add(doubleRoomRCP);
        roomCategoryPriceRepository.add(juniorSuiteRCP);
        roomCategoryPriceRepository.add(suiteRCP);

    }
}
