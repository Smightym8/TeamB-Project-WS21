package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomCategoryTests {

    @Test
    void given_roomcategorydetails_when_create_then_return_details_equals() {
        // given
        String roomCategoryIdStr = "1";
        RoomCategoryId roomCategoryId = new RoomCategoryId(roomCategoryIdStr);

        String roomCategoryNameStr = "Double Room";
        RoomCategoryName roomCategoryName = new RoomCategoryName(roomCategoryNameStr);

        String descriptionStr = "This is a double room";
        Description description = new Description(descriptionStr);

        // when
        RoomCategory roomCategory = RoomCategory.create(
                roomCategoryId,
                roomCategoryName,
                description
        );

        // then
        assertEquals(roomCategoryId, roomCategory.getRoomCategoryId());
        assertEquals(roomCategoryIdStr, roomCategory.getRoomCategoryId().id());
        assertEquals(roomCategoryName, roomCategory.getRoomCategoryName());
        assertEquals(roomCategoryNameStr, roomCategory.getRoomCategoryName().name());
        assertEquals(description, roomCategory.getDescription());
        assertEquals(descriptionStr, roomCategory.getDescription().description());
    }


}
