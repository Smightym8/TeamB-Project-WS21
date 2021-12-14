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
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.GuestRepository;
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
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

    @Autowired
    private ServiceListingService serviceListingService;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Override
    public BookingSummaryDTO createSummary(String guestId,
                                           List<String> roomCategoryIds,
                                           List<Integer> amounts,
                                           List<String> serviceIds,
                                           LocalDate checkInDate,
                                           LocalDate checkOutDate,
                                           int amountOfAdults,
                                           int amountOfChildren,
                                           String additionalInformation) throws GuestNotFoundException, ServiceNotFoundException, RoomCategoryNotFoundException {

        GuestDTO guest = guestListingService.findGuestById(guestId).orElseThrow(
                () -> new GuestNotFoundException("Guest with id " + guestId + " not found")
        );

        List<RoomCategoryDTO> roomCategories = new ArrayList<>();
        for (String s : roomCategoryIds){
            roomCategories.add(
                    roomCategoryListingService.findRoomCategoryById(s)
            );
        }

        Map<RoomCategoryDTO, Integer> categoriesWithAmounts = IntStream.range(0, roomCategories.size())
                .boxed()
                .collect(Collectors.toMap(roomCategories::get, amounts::get));

        List<ServiceDTO> services = new ArrayList<>();
        for (String s : serviceIds){
            services.add(
                    serviceListingService.findServiceById(s).orElseThrow(
                            () -> new ServiceNotFoundException("Service with id " + s + " not found")
                    )
            );
        }

        BookingSummaryDTO bookingSummaryDTO = BookingSummaryDTO.builder()
                .withGuest(guest)
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withServices(services)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAmountOfAdults(amountOfAdults)
                .withAmountOfChildren(amountOfChildren)
                .withAdditionalInformation(additionalInformation)
                .build();

        return bookingSummaryDTO;
    }

    @Override
    public BookingSummaryDTO summaryByBookingId(String bookingId) throws BookingNotFoundException, GuestNotFoundException {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + bookingId + " not found")
        );

        GuestDTO guest = guestListingService.findGuestById(booking.getGuest().getGuestId().id()).orElseThrow(
                () -> new GuestNotFoundException("Guest for booking with id " + bookingId + " not found")
        );

        Map<RoomCategoryDTO, Integer> categoriesWithAmounts = new HashMap<>();
        for (BookingWithRoomCategory brc : booking.getRoomCategories()) {
            RoomCategoryDTO roomCategoryDTO = RoomCategoryDTO.builder()
                    .withId(brc.getRoomCategory().getRoomCategoryId().id())
                    .withName(brc.getRoomCategory().getRoomCategoryName().name())
                    .build();

            categoriesWithAmounts.put(roomCategoryDTO, brc.getAmount());
        }

        List<ServiceDTO> services = new ArrayList<>();
        for(Service s : booking.getServices()) {
            services.add(
              ServiceDTO.builder()
                      .withId(s.getServiceId().id())
                      .withName(s.getServiceName().name())
                      .withPrice(s.getServicePrice().price())
                      .build()
            );
        }

        BookingSummaryDTO bookingSummaryDTO = BookingSummaryDTO.builder()
                .withGuest(guest)
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withServices(services)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .build();

        return bookingSummaryDTO;
    }

    @Override
    public BookingDetailsDTO detailsByBookingId(String bookingId) throws BookingNotFoundException, GuestNotFoundException {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + bookingId + " not found")
        );

        Guest guest = guestRepository.guestById(booking.getGuest().getGuestId()).orElseThrow(
                () -> new GuestNotFoundException("Guest for booking with id " + bookingId + " not found")
        );

        Map<String, Integer> categoriesWithAmount = new HashMap<>();
        booking.getRoomCategories().forEach(brc -> categoriesWithAmount.put(
                brc.getRoomCategory().getRoomCategoryName().name(),
                brc.getAmount()
        ));


        Map<String, BigDecimal> services = new HashMap<>();
        booking.getServices().forEach(s -> services.put(
                s.getServiceName().name(), s.getServicePrice().price()
        ));

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withId(bookingId)
                .withGuestFirstName(guest.getName().firstName())
                .withGuestLastName(guest.getName().lastName())
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withServices(services)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .withAdditionalInformation(booking.getAdditionalInformation())
                .withAmountOfAdults(booking.getAmountOfAdults())
                .withAmountOfChildren(booking.getAmountOfChildren())
                .build();

        return bookingDetailsDTO;
    }
}
