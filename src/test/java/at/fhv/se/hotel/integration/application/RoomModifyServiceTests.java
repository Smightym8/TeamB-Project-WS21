package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.RoomModifyService;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.impl.RoomModifyServiceImpl;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoomModifyServiceTests {
    @Autowired
    RoomModifyService roomModifyService;

    @MockBean
    RoomRepository roomRepository;

    @Test
    public void given_room_when_changeStatusFromCleaningToFree_then_statusShouldBeFree() throws RoomNotFoundException {
        // given
        RoomCategory roomCategory = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        String roomNameExpected = "100";
        String roomStatusExpected = "Free"; // Service should change to upper case
        Room roomExpected = Room.create(
                new RoomName(roomNameExpected),
                RoomStatus.CLEANING,
                roomCategory
        );

        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomExpected));

        // when
        roomModifyService.modifyStatus(roomNameExpected, roomStatusExpected);

        // then
        assertEquals(RoomStatus.FREE, roomExpected.getStatus());
    }

    @Test
    public void given_missingRoom_when_changeStatusFromCleaningToFree_then_RoomNotFoundExceptionIsThrown() {
        // given
        String roomNameExpected = "100";
        String roomStatusExpected = "Free"; // Service should change to upper case


        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            roomModifyService.modifyStatus(roomNameExpected, roomStatusExpected);
        });

        String expectedMessage = "Room " + roomNameExpected + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void given_occupiedRoom_when_callModifyRoom_then_InvalidParameterExceptionIsThrown() {
        // given
        RoomCategory roomCategory = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        String roomNameExpected = "100";
        String roomStatusExpected = "Free";
        Room roomExpected = Room.create(
                new RoomName(roomNameExpected),
                RoomStatus.OCCUPIED,
                roomCategory
        );

        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomExpected));

        // when ... then
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            roomModifyService.modifyStatus(roomNameExpected, roomStatusExpected);
        });

        String expectedMessage = "A room with status occupied can't be changed and it is not allowed to manually set the status to occupied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void given_room_when_changeStatusToOccupied_then_InvalidParameterExceptionIsThrown() {
        // given
        RoomCategory roomCategory = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        String roomNameExpected = "100";
        String roomStatusExpected = "Occupied";
        Room roomExpected = Room.create(
                new RoomName(roomNameExpected),
                RoomStatus.FREE,
                roomCategory
        );

        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomExpected));

        // when ... then
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            roomModifyService.modifyStatus(roomNameExpected, roomStatusExpected);
        });

        String expectedMessage = "A room with status occupied can't be changed and it is not allowed to manually set the status to occupied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
