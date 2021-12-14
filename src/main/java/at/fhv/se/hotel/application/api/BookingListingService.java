package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.BookingListingDTO;
import at.fhv.se.hotel.application.impl.BookingListingServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all bookings.
 * The implementation is in {@link BookingListingServiceImpl}.
 */
public interface BookingListingService {
    /**
     * See implementation {@link BookingListingServiceImpl#allBookings()}
     */
    List<BookingListingDTO> allBookings();
}
