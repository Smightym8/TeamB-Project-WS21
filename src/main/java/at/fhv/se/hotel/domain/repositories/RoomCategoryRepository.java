package at.fhv.se.hotel.domain.repositories;

import at.fhv.se.hotel.domain.models.RoomCategory;
import at.fhv.se.hotel.domain.models.RoomCategoryId;

import java.util.List;
import java.util.Optional;

public interface RoomCategoryRepository {
    List<RoomCategory> findAllRoomCategories();

    RoomCategoryId nextIdentity();

    void add(RoomCategory roomCategory);

    Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId);
}
