package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.impl.RoomListingServiceImpl;
import at.fhv.se.hotel.application.dto.RoomDTO;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all rooms.
 * The implementation is in {@link RoomListingServiceImpl}
 */
public interface RoomListingService {

    /**
     * See implementation {@link RoomListingServiceImpl#allRooms()}
     */
    List<RoomDTO> allRooms();

    /**
     * See implementation {@link RoomListingServiceImpl#allFreeRooms()}
     */
    List<RoomDTO> allFreeRooms();

    /**
     * See implementation {@link RoomListingServiceImpl#roomByName(String)}
     */
    RoomDTO roomByName(String name) throws RoomNotFoundException;
}
