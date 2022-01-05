package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.RoomListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import io.cucumber.java.hu.De;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoomListingServiceTests {
    @Autowired
    RoomListingService roomListingService;

    @MockBean
    RoomRepository roomRepository;

    @Test
    public void given_3roomsInRepository_when_fetchingAllRooms_then_return3MatchingRooms() {
        // given
        RoomCategory roomCategoryExpected = RoomCategory.create(
                new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Room> roomsExpected = List.of(
                Room.create(
                        new RoomName("101"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                ),
                Room.create(
                        new RoomName("102"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                ),
                Room.create(
                        new RoomName("103"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                )
        );

        Mockito.when(roomRepository.findAllRooms()).thenReturn(roomsExpected);

        // when
        List<RoomDTO> roomDTOs = roomListingService.allRooms();

        // then
        assertEquals(roomsExpected.size(), roomDTOs.size());

        for(int i = 0; i < roomsExpected.size(); i++) {
            assertEquals(roomsExpected.get(i).getName().name(), roomDTOs.get(i).name());
            assertEquals(
                    roomsExpected.get(i).getRoomCategory().getRoomCategoryName().name(),
                    roomDTOs.get(i).categoryName()
            );
            assertEquals(roomsExpected.get(i).getStatus().name(), roomDTOs.get(i).roomStatus());
        }
    }

    @Test
    public void given_3roomsInRepository_when_fetchingAllFreeRooms_then_return3FreeRooms() {
        // given
        RoomCategory roomCategoryExpected = RoomCategory.create(
                new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Room> freeRoomsExpected = List.of(
                Room.create(
                        new RoomName("101"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                ),
                Room.create(
                        new RoomName("102"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                ),
                Room.create(
                        new RoomName("103"),
                        RoomStatus.FREE,
                        roomCategoryExpected
                )
        );

        Mockito.when(roomRepository.findAllFreeRooms()).thenReturn(freeRoomsExpected);

        // when
        List<RoomDTO> roomDTOs = roomListingService.allFreeRooms();

        // then
        assertEquals(freeRoomsExpected.size(), roomDTOs.size());

        for(int i = 0; i < freeRoomsExpected.size(); i++) {
            assertEquals(freeRoomsExpected.get(i).getName().name(), roomDTOs.get(i).name());
            assertEquals(
                    freeRoomsExpected.get(i).getRoomCategory().getRoomCategoryName().name(),
                    roomDTOs.get(i).categoryName()
            );
            assertEquals(freeRoomsExpected.get(i).getStatus().name(), roomDTOs.get(i).roomStatus());
        }
    }

    @Test
    public void given_roomInRepository_when_fetchRoomByName_then_detailsEquals() throws RoomNotFoundException {
        // given
        RoomCategory roomCategory = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        String roomNameExpected = "100";
        Room roomExpected = Room.create(
                new RoomName(roomNameExpected),
                RoomStatus.FREE,
                roomCategory
        );

        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomExpected));

        // when
        RoomDTO roomActual = roomListingService.roomByName(roomNameExpected);

        // then
        assertEquals(roomNameExpected, roomActual.name());
        assertEquals(roomExpected.getStatus().name(), roomActual.roomStatus());
        assertEquals(roomExpected.getRoomCategory().getRoomCategoryName().name(), roomActual.categoryName());
    }

    @Test
    public void given_missingRoom_when_fetchRoomByName_then_RoomNotFoundExceptionIsThrown() {
        // given
        String roomNameExpected = "100";

        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            roomListingService.roomByName(roomNameExpected);
        });

        String expectedMessage = "Room " + roomNameExpected + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
