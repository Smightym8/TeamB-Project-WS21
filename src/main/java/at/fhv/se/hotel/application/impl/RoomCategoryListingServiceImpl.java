package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;

import java.util.ArrayList;
import java.util.Arrays;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RoomCategoryListingServiceImpl implements RoomCategoryListingService {

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

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
    public Optional<RoomCategoryDTO> findRoomCategoryById(String id) {
        RoomCategory roomCategory = roomCategoryRepository.roomCategoryById(new RoomCategoryId(id)).get();
        RoomCategoryDTO dto = RoomCategoryDTO.builder()
                .withId(roomCategory.getRoomCategoryId().id())
                .withName(roomCategory.getRoomCategoryName().name())
                .build();

        return Optional.of(dto);
    }
}
