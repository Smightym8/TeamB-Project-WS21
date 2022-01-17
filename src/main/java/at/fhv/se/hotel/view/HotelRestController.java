package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.BookingCreationService;
import at.fhv.se.hotel.application.api.GuestCreationService;
import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/hotel")
@CrossOrigin(origins = "http://localhost:3000")
public class HotelRestController {
    private static final String BOOKING_ROOMCATEGORIES_URL = "/roomcategories";
    private static final String BOOKING_SERVICES_URL = "/services";
    private static final String CREATE_BOOKING_URL = "/book";

    @Autowired
    RoomCategoryListingService roomCategoryListingService;

    @Autowired
    ServiceListingService serviceListingService;

    @Autowired
    GuestCreationService guestCreationService;

    @Autowired
    BookingCreationService bookingCreationService;

    @GetMapping(BOOKING_ROOMCATEGORIES_URL)
    @ResponseBody
    public RoomCategoryDTO[] allRoomCategories() {
        final List<RoomCategoryDTO> roomCategoryList = roomCategoryListingService.allRoomCategories();
        RoomCategoryDTO[] allRoomCategoriesArray = new RoomCategoryDTO[roomCategoryList.size()];

        return roomCategoryList.toArray(allRoomCategoriesArray);
    }

    @GetMapping(BOOKING_SERVICES_URL)
    @ResponseBody
    public ServiceDTO[] allServices() {
        final List<ServiceDTO> serviceList = serviceListingService.allServices();
        ServiceDTO[] allServicesArray = new ServiceDTO[serviceList.size()];

        return serviceList.toArray(allServicesArray);
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(@RequestBody ObjectNode bookingData) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader stringListReader = mapper.readerFor(new TypeReference<List<String>>() {});
        ObjectReader integerListReader = mapper.readerFor(new TypeReference<List<Integer>>() {});

        // Get Guest data and create guest for booking
        String firstName = bookingData.get("firstName").asText();
        String lastName = bookingData.get("lastName").asText();
        String gender = bookingData.get("gender").asText();
        String email = bookingData.get("eMail").asText();
        String phoneNumber = bookingData.get("phoneNumber").asText();
        LocalDate birthDate = LocalDate.parse(bookingData.get("birthDate").asText());
        String streetName = bookingData.get("streetName").asText();
        String streetNumber = bookingData.get("streetNumber").asText();
        String zipCode = bookingData.get("zipCode").asText();
        String city = bookingData.get("city").asText();
        String country = bookingData.get("country").asText();

        String guestId = guestCreationService.createGuest(
                firstName,
                lastName,
                gender,
                email,
                phoneNumber,
                birthDate,
                streetName,
                streetNumber,
                zipCode,
                city,
                country,
                0
        );

        // Create booking with booking data and guest
        List<String> roomCategoryIds;
        List<Integer> amounts;
        List<String> serviceIds;
        try {
            roomCategoryIds = stringListReader.readValue(bookingData.findValue("finalRoomCategoryIds"));
            amounts = integerListReader.readValue(bookingData.findValue("finalRoomCategoryAmounts"));
            serviceIds = stringListReader.readValue(bookingData.findValue("finalServiceIds"));
        } catch (IOException e) {
            return e.getMessage();
        }

        LocalDate checkInDate = LocalDate.parse(bookingData.get("checkInDate").asText());
        LocalDate checkOutDate = LocalDate.parse(bookingData.get("checkOutDate").asText());
        int amountOfAdults = bookingData.get("amountOfAdults").asInt();
        int amountOfChildren = bookingData.get("amountOfChildren").asInt();
        String additionalInformation = bookingData.get("additionalInformation").asText();

        String bookingId;
        try {
            bookingId = bookingCreationService.book(
                    guestId,
                    roomCategoryIds,
                    amounts,
                    serviceIds,
                    checkInDate,
                    checkOutDate,
                    amountOfAdults,
                    amountOfChildren,
                    additionalInformation
            );
        } catch (GuestNotFoundException | ServiceNotFoundException | RoomCategoryNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return bookingId;
    }
}
