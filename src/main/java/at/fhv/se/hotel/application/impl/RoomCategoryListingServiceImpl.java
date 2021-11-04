package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import java.util.Arrays;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomCategoryListingServiceImpl implements RoomCategoryListingService {

    @Override
    public List<RoomCategoryDTO> allRoomCategories() {
        final List<RoomCategoryDTO> categories = Arrays.asList(
                RoomCategoryDTO.builder()
                        .withId("1")
                        .withName("Single Room")
                        .build(),
                RoomCategoryDTO.builder()
                        .withId("2")
                        .withName("Double Room")
                        .build()
        );
        return categories;

    }

}
