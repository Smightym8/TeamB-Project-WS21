package at.fhv.se.hotel.domain.repositories;

import java.util.Optional;

public interface RoomCategoryRepository {
    List<RoomCategory> findAllRoomCategories();

    RoomCategoryId nextIdentity();

    void add(RoomCategory roomCategory);

    Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId);
}
