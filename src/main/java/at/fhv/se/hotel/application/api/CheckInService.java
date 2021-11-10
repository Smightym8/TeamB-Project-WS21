package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.impl.CheckInServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the creation of a stay.
 * The implementation is in {@link CheckInServiceImpl}.
 */
public interface CheckInService {
    /**
     * See implementation {@link CheckInServiceImpl#checkIn(String)}
     */
    void checkIn(String bookingId);
}
