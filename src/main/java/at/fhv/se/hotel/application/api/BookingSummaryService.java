package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.BookingSummaryDTO;
import at.fhv.se.hotel.application.impl.BookingListingServiceImpl;
import at.fhv.se.hotel.application.impl.BookingSummaryServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an interface that defines the creation of a booking summary.
 * The implementation is in {@link BookingSummaryServiceImpl}.
 */
public interface BookingSummaryService {
    /**
     * See implementation {@link BookingSummaryServiceImpl#createSummary(String, List, List, LocalDate, LocalDate)}
     */
    BookingSummaryDTO createSummary(String guestId,
                                    List<String> roomCategoryIds,
                                    List<String> serviceIds,
                                    LocalDate checkInDate,
                                    LocalDate checkOutDate);
}