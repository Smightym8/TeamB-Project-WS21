package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoomCategoryListingServiceImpl implements RoomCategoryListingService {

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private RoomCategoryPriceService roomCategoryPriceService;

    @Transactional(readOnly = true)
    @Override
    public List<RoomCategoryDTO> allRoomCategoriesWithPrices(LocalDate checkInDate, LocalDate checkOutDate) {
        List<Season> matchingSeasons = Season.seasons(checkInDate, checkOutDate);
        List<RoomCategory> roomCategories = roomCategoryRepository.findAllRoomCategories();

        List<RoomCategoryDTO> dtos = new ArrayList<>();

        for(RoomCategory roomCategory : roomCategories) {
            Map<String, BigDecimal> prices = new HashMap<>();
            for(Season s : matchingSeasons) {
                prices.put(s.name(), roomCategoryPriceService.by(roomCategory, s).getPrice());
            }

            RoomCategoryDTO dto = RoomCategoryDTO.builder()
                    .withId(roomCategory.getRoomCategoryId().id())
                    .withName(roomCategory.getRoomCategoryName().name())
                    .withPrices(prices)
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public Optional<RoomCategoryDTO> findRoomCategoryById(String id) {
        RoomCategory roomCategory = roomCategoryRepository.roomCategoryById(new RoomCategoryId(id)).get();
        RoomCategoryDTO dto = RoomCategoryDTO.builder()
                .withId(roomCategory.getRoomCategoryId().id())
                .withName(roomCategory.getRoomCategoryName().name())
                .build();

        return Optional.of(dto);
    }
}
