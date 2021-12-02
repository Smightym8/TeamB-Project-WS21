package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.impl.BookingCreationServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an interface that defines the creation of a booking.
 * The implementation is in {@link BookingCreationServiceImpl}.
 */
public interface BookingCreationService {
    /**
     * See implementation {@link BookingCreationServiceImpl#book(String, List, List, List, LocalDate, LocalDate)}
     */
    String book(String guestId,
                     List<String> roomCategoryIds,
                     List<Integer> amounts,
                     List<String> serviceIds,
                     LocalDate checkInDate,
                     LocalDate checkOutDate);
}
