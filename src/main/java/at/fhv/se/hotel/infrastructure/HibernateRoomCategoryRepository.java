package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;

import java.util.List;
import java.util.Optional;

public class HibernateRoomCategoryRepository implements RoomCategoryRepository {
    @Override
    public List<RoomCategory> findAllRoomCategories() {
        return null;
    }

    @Override
    public Optional<RoomCategory> roomCategoryById(RoomCategoryId roomCategoryId) {
        return Optional.empty();
    }
}
