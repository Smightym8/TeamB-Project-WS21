package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingDetailsService;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingDetailsServiceImpl implements BookingDetailsService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public BookingSummaryDTO detailsByBookingId(String bookingId) {

        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();
        GuestDTO guestDTO = GuestDTO.builder()
                .withId(booking.getGuest().getGuestId().id())
                .withFirstName(booking.getGuest().getName().firstName())
                .withLastName(booking.getGuest().getName().lastName())
                .withBirthDate(booking.getGuest().getBirthDate())
                .build();

        List<RoomCategoryDTO> categoryDtos = new ArrayList<>();
        for(RoomCategory rc : booking.getRoomCategories()) {
            RoomCategoryDTO categoryDTO = RoomCategoryDTO.builder()
                    .withId(rc.getRoomCategoryId().id())
                    .withName(rc.getRoomCategoryName().name())
                    .build();

            categoryDtos.add(categoryDTO);
        }

        List<ServiceDTO> serviceDtos = new ArrayList<>();
        for(Service s : booking.getServices()) {
            ServiceDTO serviceDTO = ServiceDTO.builder()
                    .withId(s.getServiceId().id())
                    .withName(s.getServiceName().name())
                    .withPrice(s.getServicePrice().price())
                    .build();

            serviceDtos.add(serviceDTO);
        }

        BookingSummaryDTO bookingSummaryDTO = BookingSummaryDTO.builder()
                .withGuest(guestDTO)
                .withRoomCategories(categoryDtos)
                .withServices(serviceDtos)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .build();

        return bookingSummaryDTO;
    }
}
