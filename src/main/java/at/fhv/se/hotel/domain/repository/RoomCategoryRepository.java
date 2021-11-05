package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;

import java.util.List;
import java.util.Optional;

public interface RoomCategoryRepository {
    List<RoomCategory> findAllRoomCategories();

    RoomCategoryId nextIdentity();

    void add(RoomCategory roomCategory);

    Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId);
}
