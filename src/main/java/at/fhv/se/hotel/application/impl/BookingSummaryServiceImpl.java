package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingSummaryService;
import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.dto.BookingSummaryDTO;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BookingSummaryServiceImpl implements BookingSummaryService {

    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

    @Autowired
    private ServiceListingService serviceListingService;


    @Override
    public BookingSummaryDTO createSummary(String guestId,
                                           List<String> roomCategoryIds,
                                           List<Integer> amounts,
                                           List<String> serviceIds,
                                           LocalDate checkInDate,
                                           LocalDate checkOutDate,
                                           int amountOfAdults,
                                           int amountOfChildren) {
        GuestDTO guest = guestListingService.findGuestById(guestId).get();

        List<RoomCategoryDTO> roomCategories = new ArrayList<>();
        for (String s : roomCategoryIds){
            roomCategories.add(roomCategoryListingService.findRoomCategoryById(s).get());
        }

        Map<RoomCategoryDTO, Integer> categoriesWithAmounts = IntStream.range(0, roomCategories.size())
                .boxed()
                .collect(Collectors.toMap(roomCategories::get, amounts::get));

        List<ServiceDTO> services = new ArrayList<>();
        for (String s : serviceIds){
            services.add(serviceListingService.findServiceById(s).get());
        }

        BookingSummaryDTO bookingSummaryDTO = BookingSummaryDTO.builder()
                .withGuest(guest)
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withServices(services)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAmountOfAdults(amountOfAdults)
                .withAmountOfChildren(amountOfChildren)
                .build();

        return bookingSummaryDTO;
    }
}
