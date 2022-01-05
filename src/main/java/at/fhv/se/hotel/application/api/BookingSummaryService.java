package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
import at.fhv.se.hotel.application.impl.BookingSummaryServiceImpl;
import at.fhv.se.hotel.view.forms.GuestForm;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an interface that defines the creation of a booking summary.
 * The implementation is in {@link BookingSummaryServiceImpl}.
 */
public interface BookingSummaryService {
    /**
     * See implementation
     * {@link BookingSummaryServiceImpl#createSummary(String, String, String, String, String, String, String,
     * List, List, List, LocalDate, LocalDate, int, int, String)}
     */
    BookingDetailsDTO createSummary(
            String guestId,
            String firstName,
            String lastName,
            String streetName,
            String streetNumber,
            String zipCode,
            String city,
            String country,
            List<String> roomCategoryIds,
            List<Integer> amounts,
            List<String> serviceIds,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int amountOfAdults,
            int amountOfChildren,
            String additionalInformation
    ) throws ServiceNotFoundException, RoomCategoryNotFoundException, GuestNotFoundException;

    BookingDetailsDTO detailsByBookingId(String bookingId) throws BookingNotFoundException;
}
