package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
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

    @Transactional(readOnly = true)
    @Override
    public List<RoomCategoryDTO> allRoomCategories() {
        List<RoomCategory> roomCategories = roomCategoryRepository.findAllRoomCategories();

        List<RoomCategoryDTO> dtos = new ArrayList<>();

        for(RoomCategory roomCategory : roomCategories) {
            RoomCategoryDTO dto = RoomCategoryDTO.builder()
                    .withId(roomCategory.getRoomCategoryId().id())
                    .withName(roomCategory.getRoomCategoryName().name())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public RoomCategoryDTO findRoomCategoryById(String id) throws RoomCategoryNotFoundException {
        RoomCategory roomCategory = roomCategoryRepository.roomCategoryById(new RoomCategoryId(id)).orElseThrow(
                () -> new RoomCategoryNotFoundException("Room Category with id " + id + " not found")
        );

        RoomCategoryDTO dto = RoomCategoryDTO.builder()
                .withId(roomCategory.getRoomCategoryId().id())
                .withName(roomCategory.getRoomCategoryName().name())
                .build();

        return dto;
    }
}
