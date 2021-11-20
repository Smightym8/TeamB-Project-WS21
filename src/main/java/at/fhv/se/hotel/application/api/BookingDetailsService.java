package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.BookingDTO;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
import at.fhv.se.hotel.application.dto.BookingSummaryDTO;

public interface BookingDetailsService {

    BookingDetailsDTO detailsByBookingId(String bookingId);
}
