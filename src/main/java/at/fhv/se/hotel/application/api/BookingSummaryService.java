package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
import at.fhv.se.hotel.application.dto.BookingSummaryDTO;
import at.fhv.se.hotel.application.impl.BookingSummaryServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an interface that defines the creation of a booking summary.
 * The implementation is in {@link BookingSummaryServiceImpl}.
 */
public interface BookingSummaryService {
    /**
     * See implementation
     * {@link BookingSummaryServiceImpl#createSummary(String, List, List, List, LocalDate, LocalDate, int, int, String)}
     */
    BookingSummaryDTO createSummary(String guestId,
                                    List<String> roomCategoryIds,
                                    List<Integer> amounts,
                                    List<String> serviceIds,
                                    LocalDate checkInDate,
                                    LocalDate checkOutDate,
                                    int amountOfAdults,
                                    int amountOfChildren,
                                    String additionalInformation);

    BookingSummaryDTO summaryByBookingId(String bookingId);

    BookingDetailsDTO detailsByBookingId(String bookingId);
}
