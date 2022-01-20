package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link RoomCategoryListingService}
 * It provides the functionality to get all room categories.
 */
@Component
public class RoomCategoryListingServiceImpl implements RoomCategoryListingService {

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    /**
     * This method provides all room categories.
     * @return a list of RoomCategoryDTO objects.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomCategoryDTO> allRoomCategories() {
        List<RoomCategory> roomCategories = roomCategoryRepository.findAllRoomCategories();

        List<RoomCategoryDTO> dtos = new ArrayList<>();

        for(RoomCategory roomCategory : roomCategories) {
            RoomCategoryDTO dto = RoomCategoryDTO.builder()
                    .withId(roomCategory.getRoomCategoryId().id())
                    .withName(roomCategory.getRoomCategoryName().name())
                    .withDescription(roomCategory.getDescription().description())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * This method provides a room category by id.
     * @param id contains the id of the room category.
     * @return a RoomCategoryDTO object.
     * @throws RoomCategoryNotFoundException if the room category could not be found.
     */
    @Override
    public RoomCategoryDTO findRoomCategoryById(String id) throws RoomCategoryNotFoundException {
        RoomCategory roomCategory = roomCategoryRepository.roomCategoryById(new RoomCategoryId(id)).orElseThrow(
                () -> new RoomCategoryNotFoundException("Room Category with id " + id + " not found")
        );

        RoomCategoryDTO dto = RoomCategoryDTO.builder()
                .withId(roomCategory.getRoomCategoryId().id())
                .withName(roomCategory.getRoomCategoryName().name())
                .withDescription(roomCategory.getDescription().description())
                .build();

        return dto;
    }
}
