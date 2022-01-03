package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomModifyService;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class RoomModifyServiceImpl implements RoomModifyService {
    @Autowired
    RoomRepository roomRepository;

    /**
     * This method updates the status of a room
     * @param roomName contains the name of the room whose status will be updated
     * @param roomStatus contains the new status of the room
     * @throws RoomNotFoundException if the room can't be found
     */
    @Transactional
    @Override
    public void modifyStatus(String roomName, String roomStatus) throws RoomNotFoundException {
        Room room = roomRepository.roomByName(new RoomName(roomName)).orElseThrow(
                () -> new RoomNotFoundException("Room " + roomName + " not found")
        );

        room.changeStatus(RoomStatus.valueOf(roomStatus.toUpperCase()));
    }
}