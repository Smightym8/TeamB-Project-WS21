package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.application.impl.CheckInServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the creation of a stay.
 * The implementation is in {@link CheckInServiceImpl}.
 */
public interface CheckInService {

    /**
     * See implementation {@link CheckInServiceImpl#assignRooms(String)}
     */
    List<RoomDTO> assignRooms(String bookingId) throws BookingNotFoundException;

    /**
     * See implementation {@link CheckInServiceImpl#checkIn(String, List)}
     */
    void checkIn(String bookingId, List<RoomDTO> rooms) throws BookingNotFoundException, RoomNotFoundException;
}
