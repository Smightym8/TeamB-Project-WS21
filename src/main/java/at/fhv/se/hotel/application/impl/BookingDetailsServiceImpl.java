package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingDetailsService;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookingDetailsServiceImpl implements BookingDetailsService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public BookingDetailsDTO detailsByBookingId(String bookingId) {

        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();
        GuestDTO guestDTO = GuestDTO.builder()
                .withId(booking.getGuest().getGuestId().id())
                .withFirstName(booking.getGuest().getName().firstName())
                .withLastName(booking.getGuest().getName().lastName())
                .withBirthDate(booking.getGuest().getBirthDate())
                .build();

        Map<RoomCategoryDTO, Integer> categoriesWithAmount = new HashMap<>();
        for(BookingWithRoomCategory brc : booking.getRoomCategories()) {
            RoomCategoryDTO categoryDTO = RoomCategoryDTO.builder()
                    .withId(brc.getRoomCategory().getRoomCategoryId().id())
                    .withName(brc.getRoomCategory().getRoomCategoryName().name())
                    .build();
            Integer amount = brc.getAmount();
            categoriesWithAmount.put(categoryDTO, amount);
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

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withId(bookingId)
                .withGuest(guestDTO)
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withServices(serviceDtos)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .withAmountOfAdults(booking.getAmountOfAdults())
                .withAmountOfChildren(booking.getAmountOfChildren())
                .build();

        return bookingDetailsDTO;
    }
}
