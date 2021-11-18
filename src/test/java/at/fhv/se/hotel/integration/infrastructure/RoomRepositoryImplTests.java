package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class RoomRepositoryImplTests {
    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    void given_3freesingleroomsinrepository_when_fetchfreesinglerooms_then_returnequalrooms() {
        // given
        List<String> roomNamesExpected = Arrays.asList("Room 1", "Room 2", "Room 3");

        RoomCategory roomCategoryExpected = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(name, roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        // when
        roomsExpected.forEach(room -> this.roomRepository.add(room));
        List<Room> roomsActual = this.roomRepository.roomsByCategoryAndStatus(
                roomCategoryExpected.getRoomCategoryId(),
                roomStatusExpected);

        // then
        assertEquals(roomsExpected.size(), roomsActual.size());
        for(Room r : roomsActual) {
            assertTrue(roomsExpected.contains(r));
        }
    }
}