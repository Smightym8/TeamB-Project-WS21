package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingCreationService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link BookingCreationService}.
 * It provides the functionality to create a booking.
 */
@Component
public class BookingCreationServiceImpl implements BookingCreationService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * This method creates a new booking and saves it into the database.
     * @param guestId contains the id of the guest.
     * @param roomCategoryIds contains the id of the room categories.
     * @param amounts contains the number of booked room categories.
     * @param serviceIds contains the ids of the services.
     * @param checkInDate contains the check-in date.
     * @param checkOutDate contains the check-out date.
     * @param amountOfAdults contains the number of adults.
     * @param amountOfChildren contains the number of children.
     * @param additionalInformation contains additional information.
     * @return The bookingId.
     * @throws GuestNotFoundException if the guest could not be found.
     * @throws ServiceNotFoundException if the service could not be found.
     * @throws RoomCategoryNotFoundException if the room category could not be found.
     */
    @Transactional
    @Override
    public String book(String guestId,
                     List<String> roomCategoryIds,
                     List<Integer> amounts,
                     List<String> serviceIds,
                     LocalDate checkInDate,
                     LocalDate checkOutDate,
                     int amountOfAdults,
                     int amountOfChildren,
                     String additionalInformation) throws GuestNotFoundException, ServiceNotFoundException, RoomCategoryNotFoundException {

        BookingId bookingId = bookingRepository.nextIdentity();

        int todaysBookingsAmount = bookingRepository.amountOfBookingsByDate(LocalDate.now()) + 1;
        String bookingSuffix = "";

        if(todaysBookingsAmount < 10) {
            bookingSuffix = "00" + todaysBookingsAmount;
        } else if(todaysBookingsAmount < 100) {
            bookingSuffix = "0" + todaysBookingsAmount;
        } else {
            bookingSuffix = String.valueOf(todaysBookingsAmount);
        }

        String bookingNumber = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + bookingSuffix;

        Guest guest = guestRepository.guestById(new GuestId(guestId)).orElseThrow(
                () -> new GuestNotFoundException("Guest with id " + guestId + " not found")
        );

        List<Service> services = new ArrayList<>();
        for (String s : serviceIds) {
            Service service = serviceRepository.serviceById(new ServiceId(s)).orElseThrow(
                    () -> new ServiceNotFoundException("Service with id " + s + " not found")
            );
            services.add(service);
        }

        Booking booking = Booking.create(
                checkInDate, checkOutDate, bookingId,
                guest, services, amountOfAdults, amountOfChildren,
                additionalInformation, bookingNumber
        );

        int i = 0;
        for (String s : roomCategoryIds) {
            if (amounts.get(i) > 0) {
                RoomCategory category = roomCategoryRepository.roomCategoryById(new RoomCategoryId(s)).orElseThrow(
                        () -> new RoomCategoryNotFoundException("RoomCategory with id " + s + " not found")
                );

                booking.addRoomCategory(category, amounts.get(i));
            }

            i++;
        }

        bookingRepository.add(booking);

        return booking.getBookingId().id();
    }
}
