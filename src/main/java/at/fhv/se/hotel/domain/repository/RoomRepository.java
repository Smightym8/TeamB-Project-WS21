package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAllRooms();

    List<Room> findAllFreeRooms();

    Optional<Room> roomByName(RoomName name);

    List<Room> roomsByCategoryAndStatus(RoomCategoryId categoryId, RoomStatus status);

    void add(Room room);
}
