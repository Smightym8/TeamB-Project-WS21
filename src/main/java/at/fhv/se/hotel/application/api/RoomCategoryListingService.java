package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.impl.RoomCategoryListingServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * This class represents an interface that defines the functionality to get all bookings.
 * The implementation is in {@link RoomCategoryListingServiceImpl}.
 */
public interface RoomCategoryListingService {
    /**
     * See implementation {@link RoomCategoryListingServiceImpl#allRoomCategories )}
     */
    List<RoomCategoryDTO> allRoomCategories();
    Optional<RoomCategoryDTO> findRoomCategoryById(String id);
}
