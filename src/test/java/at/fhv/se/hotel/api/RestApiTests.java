package at.fhv.se.hotel.api;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    RoomCategoryListingService roomCategoryListingService;

    @MockBean
    ServiceListingService serviceListingService;

    @MockBean
    GuestCreationService guestCreationService;

    @MockBean
    BookingCreationService bookingCreationService;

    @MockBean
    BookingSummaryService bookingSummaryService;

    @Test
    public void given_roomCategories_when_fetchingAllCategoriesThroughRest_then_returnEqualsCategories() {
        // given
        List<RoomCategoryDTO> categoryDTOsExpected = List.of(
                RoomCategoryDTO.builder()
                        .withId("1")
                        .withName("Single Room")
                        .build(),
                RoomCategoryDTO.builder()
                        .withId("2")
                        .withName("Double Room")
                        .build()
        );

        Mockito.when(roomCategoryListingService.allRoomCategories()).thenReturn(categoryDTOsExpected);

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/hotel/roomcategories").build().encode().toUri();

        RoomCategoryDTO[] categoryDTOsActual = this.restTemplate.getForObject(uri, RoomCategoryDTO[].class);

        // then
        assertEquals(categoryDTOsExpected.size(), categoryDTOsActual.length);

        for(int i = 0; i < categoryDTOsExpected.size(); i++) {
            assertEquals(categoryDTOsExpected.get(i).id(), categoryDTOsActual[i].id());
            assertEquals(categoryDTOsExpected.get(i).name(), categoryDTOsActual[i].name());
        }
    }

    @Test
    public void given_services_when_fetchingAllServicesThroughRest_then_returnEqualsServices() {
        // given
        List<ServiceDTO> serviceDTOsExpected = List.of(
                ServiceDTO.builder().withId("1").withName("TV").withPrice(new BigDecimal("10")).build(),
                ServiceDTO.builder().withId("2").withName("Newspaper").withPrice(new BigDecimal("5")).build()
        );

        Mockito.when(serviceListingService.allServices()).thenReturn(serviceDTOsExpected);

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/hotel/services").build().encode().toUri();

        ServiceDTO[] serviceDTOsActual = this.restTemplate.getForObject(uri, ServiceDTO[].class);

        // then
        assertEquals(serviceDTOsExpected.size(), serviceDTOsActual.length);

        for(int i = 0; i < serviceDTOsExpected.size(); i++) {
            assertEquals(serviceDTOsExpected.get(i).id(), serviceDTOsActual[i].id());
            assertEquals(serviceDTOsExpected.get(i).name(), serviceDTOsActual[i].name());
            assertEquals(serviceDTOsExpected.get(i).price(), serviceDTOsActual[i].price());
        }
    }

    @Test
    public void given_bookingData_when_book_then_returnBookingId() throws RoomCategoryNotFoundException, ServiceNotFoundException, GuestNotFoundException, BookingNotFoundException {
        // given
        String firstName = "John";
        String lastName = "Doe";
        String gender = "Male";
        String email = "john.doe@gmail.com";
        String phoneNumber = "+43 12 12 12";
        LocalDate birthDate = LocalDate.of(1980, 1, 1);
        String streetName = "Street";
        String streetNumber = "1";
        String zipCode = "6850";
        String city = "Dornbirn";
        String country = "Austria";
        double discountInPercent = 0;

        String bookingId = "1";
        String guestId = "1";
        List<String> roomCategoryIds = List.of("1");
        List<Integer> amounts = List.of(2);
        Map<String, Integer> categoriesWithAmount = Map.of("Single Room", 2);
        List<String> serviceIds = List.of("1", "2");
        Map<String, BigDecimal> services = Map.of("TV", new BigDecimal("10"), "WLAN", new BigDecimal("5"));
        LocalDate checkInDate = LocalDate.of(2022, 1, 15);
        LocalDate checkOutDate = LocalDate.of(2022, 1, 20);
        int amountOfAdults = 2;
        int amountOfChildren = 0;
        String additionalInformation = "Vegan";
        String bookingNumberExpected = checkInDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withBookingId(bookingId)
                .withGuestFirstName(firstName)
                .withGuestLastName(lastName)
                .withStreetName(streetName)
                .withStreetNumber(streetNumber)
                .withZipCode(zipCode)
                .withCity(city)
                .withCountry(country)
                .withRoomCategoriesAndAmounts(categoriesWithAmount)
                .withCategoryIds(roomCategoryIds)
                .withServices(services)
                .withServiceIds(serviceIds)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAdditionalInformation(additionalInformation)
                .withAmountOfAdults(amountOfAdults)
                .withAmountOfChildren(amountOfChildren)
                .withBookingNumber(bookingNumberExpected)
                .build();

        Mockito.when(guestCreationService.createGuest(
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
                discountInPercent
        )).thenReturn(guestId);

        Mockito.when(bookingCreationService.book(
                guestId,
                roomCategoryIds,
                amounts,
                serviceIds,
                checkInDate,
                checkOutDate,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        )).thenReturn(bookingId);

        Mockito.when(bookingSummaryService.detailsByBookingId(bookingId)).thenReturn(bookingDetailsDTO);

        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode requestData = mapper.createObjectNode();
        requestData.set("firstName", mapper.convertValue(firstName, JsonNode.class));
        requestData.set("lastName", mapper.convertValue(lastName, JsonNode.class));
        requestData.set("gender", mapper.convertValue(gender, JsonNode.class));
        requestData.set("eMail", mapper.convertValue(email, JsonNode.class));
        requestData.set("phoneNumber", mapper.convertValue(phoneNumber, JsonNode.class));
        requestData.set("birthDate", mapper.convertValue(birthDate.toString(), JsonNode.class));
        requestData.set("streetName", mapper.convertValue(streetName, JsonNode.class));
        requestData.set("streetNumber", mapper.convertValue(streetNumber, JsonNode.class));
        requestData.set("zipCode", mapper.convertValue(zipCode, JsonNode.class));
        requestData.set("city", mapper.convertValue(city, JsonNode.class));
        requestData.set("country", mapper.convertValue(country, JsonNode.class));

        requestData.set("finalRoomCategoryIds", mapper.convertValue(roomCategoryIds, JsonNode.class));
        requestData.set("finalRoomCategoryAmounts", mapper.convertValue(amounts, JsonNode.class));
        requestData.set("finalServiceIds", mapper.convertValue(serviceIds, JsonNode.class));
        requestData.set("checkInDate", mapper.convertValue(checkInDate.toString(), JsonNode.class));
        requestData.set("checkOutDate", mapper.convertValue(checkOutDate.toString(), JsonNode.class));
        requestData.set("amountOfAdults", mapper.convertValue(amountOfAdults, JsonNode.class));
        requestData.set("amountOfChildren", mapper.convertValue(amountOfChildren, JsonNode.class));
        requestData.set("additionalInformation", mapper.convertValue(additionalInformation, JsonNode.class));

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/hotel/book").build().encode().toUri();

        String bookingNumberActual = this.restTemplate.postForObject(uri, requestData, String.class);

        // then
        assertEquals(bookingNumberExpected, bookingNumberActual);
    }
}
