package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.RoomDTO;

import java.util.List;

public interface RoomListingService {
    List<RoomDTO> allRooms();
}
