package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;

import java.util.List;

public interface RoomListingService {
    List<RoomDTO> allRooms();

    List<RoomDTO> allFreeRooms();

    RoomDTO roomByName(String name) throws RoomNotFoundException;
}
