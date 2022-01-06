package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;

public interface RoomModifyService {
    /**
     * See implementation
     * {@link at.fhv.se.hotel.application.impl.RoomModifyServiceImpl#modifyStatus(String, String)}
     */
    void modifyStatus(String roomName, String roomStatus) throws RoomNotFoundException;
}
