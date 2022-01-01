package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.RoomListingService;
import at.fhv.se.hotel.application.dto.RoomDTO;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
