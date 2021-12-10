package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
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
                                    String additionalInformation) throws GuestNotFoundException, ServiceNotFoundException, RoomCategoryNotFoundException;

    BookingSummaryDTO summaryByBookingId(String bookingId) throws BookingNotFoundException, GuestNotFoundException;

    BookingDetailsDTO detailsByBookingId(String bookingId) throws BookingNotFoundException, GuestNotFoundException;
}
