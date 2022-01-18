package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingSummaryService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
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

/**
 * This class represents the implementation of the interface {@link BookingSummaryService}
 * It provides the functionality to get a booking summary after the creation of a booking or later by id.
 */
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

    /**
     * This method provides a summary of the booking.
     * @param guestId contains the id of the guest.
     * @param firstName contains the first name of the guest.
     * @param lastName contains the last name of the guest.
     * @param streetName contains the street name of the address of the guest.
     * @param streetNumber contains the street number of the address of the guest.
     * @param zipCode contains the zip code of the address of the guest.
     * @param city contains the city of the address of the guest.
     * @param country contains the country of the address of the guest.
     * @param roomCategoryIds contains
     * @param amounts contains the number of booked room categories.
     * @param serviceIds contains the ids of the services.
     * @param checkInDate contains the check-in date.
     * @param checkOutDate contains the check-out date.
     * @param amountOfAdults contains the number of adults.
     * @param amountOfChildren contains the number of children.
     * @param additionalInformation contains additional information.
     * @return A BookingDetailsDTO object.
     * @throws ServiceNotFoundException if the service could not be found.
     * @throws RoomCategoryNotFoundException if the room category could not be found.
     * @throws GuestNotFoundException if the guest could not be found.
     */
    @Override
    public BookingDetailsDTO createSummary(
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
    ) throws ServiceNotFoundException, RoomCategoryNotFoundException, GuestNotFoundException {

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

        if(guestId == null || guestId.isEmpty()) {
            return BookingDetailsDTO.builder()
                    .withGuestFirstName(firstName)
                    .withGuestLastName(lastName)
                    .withStreetName(streetName)
                    .withStreetNumber(streetNumber)
                    .withZipCode(zipCode)
                    .withCity(city)
                    .withCountry(country)
                    .withRoomCategoriesAndAmounts(categoriesWithAmount)
                    .withServices(services)
                    .withCheckInDate(checkInDate)
                    .withCheckOutDate(checkOutDate)
                    .withAdditionalInformation(additionalInformation)
                    .withAmountOfAdults(amountOfAdults)
                    .withAmountOfChildren(amountOfChildren)
                    .build();
        } else {
            Guest guest = guestRepository.guestById(new GuestId(guestId)).orElseThrow(
                    () -> new GuestNotFoundException("Guest with id " + guestId + " not found")
            );

            return BookingDetailsDTO.builder()
                    .withGuestFirstName(guest.getName().firstName())
                    .withGuestLastName(guest.getName().lastName())
                    .withStreetName(guest.getAddress().streetName())
                    .withStreetNumber(guest.getAddress().streetNumber())
                    .withZipCode(guest.getAddress().zipCode())
                    .withCity(guest.getAddress().city())
                    .withCountry(guest.getAddress().country())
                    .withRoomCategoriesAndAmounts(categoriesWithAmount)
                    .withServices(services)
                    .withCheckInDate(checkInDate)
                    .withCheckOutDate(checkOutDate)
                    .withAdditionalInformation(additionalInformation)
                    .withAmountOfAdults(amountOfAdults)
                    .withAmountOfChildren(amountOfChildren)
                    .build();
        }
    }

    /**
     * This method provides details of a booking by id.
     * @param bookingId contains the booking id.
     * @return A BookingDetailsDTO object.
     * @throws BookingNotFoundException if the booking could not be found.
     */
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
                .withStreetName(booking.getGuest().getAddress().streetName())
                .withStreetNumber(booking.getGuest().getAddress().streetNumber())
                .withZipCode(booking.getGuest().getAddress().zipCode())
                .withCity(booking.getGuest().getAddress().city())
                .withCountry(booking.getGuest().getAddress().country())
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withCategoryIds(categoryIds)
                .withServices(services)
                .withServiceIds(serviceIds)
                .withCheckInDate(booking.getCheckInDate())
                .withCheckOutDate(booking.getCheckOutDate())
                .withAdditionalInformation(booking.getAdditionalInformation())
                .withAmountOfAdults(booking.getAmountOfAdults())
                .withAmountOfChildren(booking.getAmountOfChildren())
                .withBookingNumber(booking.getBookingNumber())
                .build();

        return bookingDetailsDTO;
    }
}
