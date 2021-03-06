package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.impl.RoomCategoryListingServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all room categories or a specific one by id.
 * The implementation is in {@link RoomCategoryListingServiceImpl}.
 */
public interface RoomCategoryListingService {
    /**
     * See implementation {@link RoomCategoryListingServiceImpl#allRoomCategories()}
     */
    List<RoomCategoryDTO> allRoomCategories();

    /**
     * See implementation {@link RoomCategoryListingServiceImpl#findRoomCategoryById(String)}  )}
     */
    RoomCategoryDTO findRoomCategoryById(String id) throws RoomCategoryNotFoundException;
}
