package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingSummaryService;
import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: Write test to ensure exception is thrown
@Component
public class BookingSummaryServiceImpl implements BookingSummaryService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public BookingDetailsDTO createSummary(String guestId,
                                           List<String> roomCategoryIds,
                                           List<Integer> amounts,
                                           List<String> serviceIds,
                                           LocalDate checkInDate,
                                           LocalDate checkOutDate,
                                           int amountOfAdults,
                                           int amountOfChildren,
                                           String additionalInformation) throws GuestNotFoundException, ServiceNotFoundException, RoomCategoryNotFoundException {

        Guest guest = guestRepository.guestById(new GuestId(guestId)).orElseThrow(
                () -> new GuestNotFoundException("Guest with id " + guestId + " not found")
        );

        Map<String, Integer> categoriesWithAmount = new HashMap<>();
        int i = 0;
        for(String roomCategoryId : roomCategoryIds) {
            RoomCategory roomCategory = roomCategoryRepository.roomCategoryById(new RoomCategoryId(roomCategoryId)).orElseThrow(
                    () -> new RoomCategoryNotFoundException("Category with id " + roomCategoryId + " not found")
            );

            categoriesWithAmount.put(roomCategory.getRoomCategoryName().name(), amounts.get(i));
            i++;
        }

        Map<String, BigDecimal> services = new HashMap<>();
        for(String serviceId : serviceIds) {
            Service service = serviceRepository.serviceById(new ServiceId(serviceId)).orElseThrow(
                    () -> new ServiceNotFoundException("Service with id " + serviceId + " not found")
            );

            services.put(service.getServiceName().name(), service.getServicePrice().price());
        }

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withGuestId(guestId)
                .withGuestFirstName(guest.getName().firstName())
                .withGuestLastName(guest.getName().lastName())
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withServices(services)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAdditionalInformation(additionalInformation)
                .withAmountOfAdults(amountOfAdults)
                .withAmountOfChildren(amountOfChildren)
                .build();

        return bookingDetailsDTO;
    }

    @Override
    public BookingDetailsDTO detailsByBookingId(String bookingId) throws BookingNotFoundException {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + bookingId + " not found")
        );

        Map<String, Integer> categoriesWithAmount = new HashMap<>();
        List<String> categoryIds = new ArrayList<>();
        booking.getRoomCategories().forEach(brc -> {
            categoriesWithAmount.put(
                brc.getRoomCategory().getRoomCategoryName().name(),
                brc.getAmount());

            categoryIds.add(brc.getRoomCategory().getRoomCategoryId().id());
        });

        Map<String, BigDecimal> services = new HashMap<>();
        List<String> serviceIds = new ArrayList<>();
        booking.getServices().forEach(s -> {
            services.put(
                    s.getServiceName().name(), s.getServicePrice().price()
            );

            serviceIds.add(s.getServiceId().id());
        });

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withBookingId(bookingId)
                .withGuestFirstName(booking.getGuest().getName().firstName())
                .withGuestLastName(booking.getGuest().getName().lastName())
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withCategoryIds(categoryIds)
                .withServices(services)
                .withServiceIds(serviceIds)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .withAdditionalInformation(booking.getAdditionalInformation())
                .withAmountOfAdults(booking.getAmountOfAdults())
                .withAmountOfChildren(booking.getAmountOfChildren())
                .build();

        return bookingDetailsDTO;
    }
}
