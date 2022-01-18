package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.impl.RoomModifyServiceImpl;

/**
 * This class represents an interface that defines the functionality to modify a room.
 * The implementation is in {@link RoomModifyServiceImpl}
 */
public interface RoomModifyService {
    /**
     * See implementation
     * {@link RoomModifyServiceImpl#modifyStatus(String, String)}
     */
    void modifyStatus(String roomName, String roomStatus) throws RoomNotFoundException;
}
