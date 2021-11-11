package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.BookingDTO;
import at.fhv.se.hotel.application.dto.BookingSummaryDTO;

public interface BookingDetailsService {

    BookingSummaryDTO detailsByBookingId(String bookingId);
}
