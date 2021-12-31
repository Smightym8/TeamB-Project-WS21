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

@Component
public class SeasonCreationServiceImpl implements SeasonCreationService {
    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

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
