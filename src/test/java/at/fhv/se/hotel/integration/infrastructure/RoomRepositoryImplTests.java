package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class RoomRepositoryImplTests {
    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_3freesingleroomsinrepository_when_fetchfreesinglerooms_then_returnequalrooms() {
        // given
        List<String> roomNamesExpected = Arrays.asList("Room 1", "Room 2", "Room 3");

        RoomCategory roomCategoryExpected = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        // when
        roomsExpected.forEach(room -> this.roomRepository.add(room));
        em.flush();
        List<Room> roomsActual = this.roomRepository.roomsByCategoryAndStatus(
                roomCategoryExpected.getRoomCategoryId(),
                roomStatusExpected);

        // then
        assertEquals(roomsExpected.size(), roomsActual.size());
        for(Room r : roomsActual) {
            assertTrue(roomsExpected.contains(r));
        }
    }

    @Test
    void given_room_when_fetchingbyname_then_returnequalsroom() {
        // given
        RoomCategory singleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room Test"),
                new Description("This is a single room")
        );

        Room roomExpected = Room.create(new RoomName("101"), RoomStatus.FREE, singleRoom);

        this.roomCategoryRepository.add(singleRoom);
        this.roomRepository.add(roomExpected);
        this.em.flush();

        // when
        Room roomActual = roomRepository.roomByName(new RoomName("101")).get();

        // then
        assertEquals(roomExpected, roomActual);
        assertEquals(roomExpected.getRoomCategory(), roomActual.getRoomCategory());
        assertEquals(roomExpected.getName(), roomActual.getName());
        assertEquals(roomExpected.getStatus(), roomActual.getStatus());
    }

    @Test
    void given_3rooms_when_addRoomsToRepository_then_return3EqualsRooms() {
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
        roomCategoryRepository.add(roomCategoryExpected);
        roomsExpected.forEach(room -> roomRepository.add(room));
        em.flush();

        // when
        List<Room> roomsActual = roomRepository.findAllRooms();

        // then
        assertEquals(roomsExpected.size(), roomsActual.size());

        for(Room r : roomsActual) {
            assertTrue(roomsExpected.contains(r));
        }
    }
}
