package at.fhv.se.hotel.domain.repositories;

import at.fhv.se.hotel.domain.models.RoomCategory.RoomCategory;
import at.fhv.se.hotel.domain.models.RoomCategory.RoomCategoryId;

import java.util.List;
import java.util.Optional;

public interface RoomCategoryRepository {
    List<RoomCategory> findAllRoomCategories();

    RoomCategoryId nextIdentity();

    void add(RoomCategory roomCategory);

    Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId);
}
