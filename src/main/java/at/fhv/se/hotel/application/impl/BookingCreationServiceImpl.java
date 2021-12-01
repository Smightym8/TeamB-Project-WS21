package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingCreationService;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingCreationServiceImpl implements BookingCreationService {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Transactional(readOnly = false)
    @Override
    public void book(String guestId,
                     List<String> roomCategoryIds,
                     List<Integer> amounts,
                     List<String> serviceIds,
                     LocalDate checkInDate,
                     LocalDate checkOutDate,
                     int amountOfAdults,
                     int amountOfChildren,
                     String additionalInformation) {

        BookingId bookingId = bookingRepository.nextIdentity();

        Guest guest = guestRepository.guestById(new GuestId(guestId)).get();

        List<Service> services = new ArrayList<>();
        for (String s : serviceIds) {
            Service service = serviceRepository.serviceById(new ServiceId(s)).get();
            services.add(service);
        }

        Booking booking = Booking.create(
                checkInDate, checkOutDate, bookingId,
                guest, services, amountOfAdults, amountOfChildren, additionalInformation
        );

        int i = 0;
        for (String s : roomCategoryIds) {
            if (amounts.get(i) > 0) {
                RoomCategory category = roomCategoryRepository.roomCategoryById(new RoomCategoryId(s)).get();
                booking.addRoomCategory(category, amounts.get(i));
            }
            i++;
        }

        bookingRepository.add(booking);
    }
}
