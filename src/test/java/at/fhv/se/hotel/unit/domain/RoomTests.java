package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTests {

    @Test
    void given_roomDetails_when_createRoom_then_detailsEquals() {
        // given
        String nameExpectedStr = "S101";
        RoomName roomNameExpected = new RoomName(nameExpectedStr);
        RoomStatus statusExpected = RoomStatus.FREE;
        RoomCategory roomCategoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single Room")
        );

        // when
        Room room = Room.create(
                roomNameExpected,
                statusExpected,
                roomCategoryExpected
        );

        // then
        assertEquals(roomNameExpected, room.getName());
        assertEquals(nameExpectedStr, room.getName().name());
        assertEquals(statusExpected, room.getStatus());
        assertEquals(roomCategoryExpected, room.getRoomCategory());
    }
}
