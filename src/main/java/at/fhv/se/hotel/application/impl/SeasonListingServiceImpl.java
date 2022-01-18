package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.SeasonListingService;
import at.fhv.se.hotel.application.dto.SeasonWithPricesDTO;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the implementation of the interface {@link SeasonListingService}
 * It provides the functionality to get all seasons with prices.
 */
@Component
public class SeasonListingServiceImpl implements SeasonListingService {
    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    /**
     * This method provides all seasons with prices.
     * @return a list of SeasonWithPricesDTO objects.
     */
    @Override
    public List<SeasonWithPricesDTO> allSeasonsWithPrices() {
        List<Season> seasons = seasonRepository.findAllSeasons();
        List<RoomCategory> categories = roomCategoryRepository.findAllRoomCategories();

        List<SeasonWithPricesDTO> dtos = new ArrayList<>();
        for(Season season : seasons) {
            List<BigDecimal> prices = new ArrayList<>();
            for(RoomCategory category : categories) {
                Optional<RoomCategoryPrice> roomCategoryPrice = roomCategoryPriceRepository.priceBySeasonAndCategory(
                        season.getSeasonId(), category.getRoomCategoryId()
                );

                // Add only seasons that have a price
                roomCategoryPrice.ifPresent(categoryPrice -> prices.add(categoryPrice.getPrice()));
            }

            // Add only seasons that have a price
            if(prices.size() > 0) {
                SeasonWithPricesDTO dto = SeasonWithPricesDTO.builder()
                        .withSeasonName(season.getSeasonName().name())
                        .withStartDate(season.getStartDate())
                        .withEndDate(season.getEndDate())
                        .withPrices(prices)
                        .build();

                dtos.add(dto);
            }
        }

        return dtos;
    }
}
