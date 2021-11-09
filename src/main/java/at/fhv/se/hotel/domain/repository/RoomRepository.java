package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;

import java.util.List;

public interface RoomRepository {
    List<Room> roomsByCategoryAndStatus(RoomCategoryId categoryId, RoomStatus status);

    void add(Room room);
}
