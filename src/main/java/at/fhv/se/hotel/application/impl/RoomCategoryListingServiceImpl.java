package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;

import java.util.ArrayList;
import java.util.Arrays;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
