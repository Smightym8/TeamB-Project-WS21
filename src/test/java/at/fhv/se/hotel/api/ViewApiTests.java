package at.fhv.se.hotel.api;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewApiTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookingListingService bookingListingService;

    @MockBean
    StayListingService stayListingService;

    @MockBean
    GuestListingService guestListingService;

    @MockBean
    GuestCreationService guestCreationService;

    @MockBean
    RoomCategoryListingService roomCategoryListingService;

    @MockBean
    ServiceListingService serviceListingService;

    @MockBean
    BookingSummaryService bookingSummaryService;

    @MockBean
    BookingCreationService bookingCreationService;

    @MockBean
    InvoiceListingService invoiceListingService;

    @Test
    public void when_get_rootUrl_then_statusOk_and_homeView_and_allBookings_and_allStays_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/home"));

        // then
        Mockito.verify(bookingListingService, times(1)).allBookings();
        Mockito.verify(stayListingService, times(1)).allStays();
    }

    @Test
    public void when_get_roomsUrl_then_statusOk_and_roomsView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/rooms").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/rooms"));
    }

    @Test
    public void when_get_pricingUrl_then_statusOk_and_pricingView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/pricing").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/pricing"));
    }

    @Test
    public void when_get_guestsUrl_then_statusOk_and_guestsView_and_allGuests_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/guests").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/guests"));

        // then
        Mockito.verify(guestListingService, times(1)).allGuests();
    }

    @Test
    public void when_get_bookingsUrl_then_statusOk_and_bookingsView_and_allBookings_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/bookings").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/bookings"));

        // then
        Mockito.verify(bookingListingService, times(1)).allBookings();
    }

    @Test
    public void when_get_staysUrl_then_statusOk_and_staysView_and_allStays_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/stays").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/stays"));

        // then
        Mockito.verify(stayListingService, times(1)).allStays();
    }

    @Test
    public void when_get_invoicesUrl_then_statusOk_and_invoicesView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/invoices").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/invoices"));
    }

    @Test
    public void when_get_createGuestUrl_then_statusOk_and_createGuestView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/createguest").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("createGuest"));
    }

    @Test
    public void when_post_createGuestUrl_then_statusRedirect_and_redirectToGuestsView_and_createGuest_called()
            throws Exception {
        // given
        String firstNameExpected = "John";
        String lastNameExpected = "Doe";
        Gender genderExpected = Gender.MALE;
        String emailExpected = "john.doe@developer.tdd.at";
        String phoneNumberExpected = "+43 123 456 789";
        LocalDate birthDateExpected = LocalDate.of(1970, 1, 1);
        String streetNameExpected = "Street";
        String streetNumberExpected = "42";
        String zipCodeExpected = "6850";
        String cityExpected = "Dornbirn";
        String countryExpected = "Austria";

        GuestForm guestForm = new GuestForm();
        guestForm.setFirstName(firstNameExpected);
        guestForm.setLastName(lastNameExpected);
        guestForm.setGender(genderExpected.name());
        guestForm.seteMail(emailExpected);
        guestForm.setPhoneNumber(phoneNumberExpected);
        guestForm.setBirthDate(birthDateExpected);
        guestForm.setStreetName(streetNameExpected);
        guestForm.setStreetNumber(streetNumberExpected);
        guestForm.setZipCode(zipCodeExpected);
        guestForm.setCity(cityExpected);
        guestForm.setCountry(countryExpected);

        // when ... then
        this.mockMvc.perform(post("/createguest")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "firstName", firstNameExpected,
                        "lastName", lastNameExpected,
                        "eMail", emailExpected,
                        "phoneNumber", phoneNumberExpected,
                        "birthDate", String.valueOf(birthDateExpected),
                        "gender", genderExpected.name(),
                        "streetName", streetNameExpected,
                        "streetNumber", streetNumberExpected,
                        "zipCode", zipCodeExpected,
                        "city", cityExpected,
                        "country", countryExpected
                ))
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                //.andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().is3xxRedirection()) // After creation redirect to guests
                .andExpect(view().name("redirect:/guests"));

        // then
        Mockito.verify(guestCreationService, times(1))
                .createGuest(
                        firstNameExpected,
                        lastNameExpected,
                        genderExpected.name(),
                        emailExpected,
                        phoneNumberExpected,
                        birthDateExpected,
                        streetNameExpected,
                        streetNumberExpected,
                        zipCodeExpected,
                        cityExpected,
                        countryExpected
                );
    }

    @Test
    public void when_post_createGuestUrl_with_invalidForm_then_statusOk_and_createGuestView_called()
            throws Exception {
        // given
        String firstNameExpected = "John";
        String lastNameExpected = "Doe";

        // when ... then
        this.mockMvc.perform(post("/createguest")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "firstName", firstNameExpected,
                                "lastName", lastNameExpected
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                //.andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(view().name("createGuest"));
    }

    @Test
    public void when_get_createBookingGuestUrl_then_statusOk_and_createBookingGuestView_and_allGuests_called()
            throws Exception {
        // when ... then
        this.mockMvc.perform(get("/createbooking/guest").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingGuest"));

        // then
        Mockito.verify(guestListingService, times(1)).allGuests();
    }

    @Test
    public void when_post_createBookingDateUrl_then_statusOk_and_createBookingDateView_called() throws Exception {
        // given
        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";

        // when ... then
        this.mockMvc.perform(post("/createbooking/date")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "guestId", guestIdExpected,
                                "amountOfAdults", amountOfAdultsExpected
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingDate"));
    }

    @Test
    public void when_post_createBookingCategoryUrl_then_statusOk_and_createBookingCategoryView_and_allRoomCategories_called()
            throws Exception {
        // given
        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);

        // when ... then
        this.mockMvc.perform(post("/createbooking/category")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "guestId", guestIdExpected,
                                "amountOfAdults", amountOfAdultsExpected,
                                "checkInDate", String.valueOf(checkInDateExpected),
                                "checkOutDate", String.valueOf(checkOutDateExpected)
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingCategory"));

        // then
        Mockito.verify(roomCategoryListingService, times(1)).allRoomCategories();
    }

    @Test
    public void when_post_createBookingServiceUrl_then_statusOk_and_createBookingServiceView_and_allServices_called()
            throws Exception {
        // given
        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIdsExoected = "1,2";
        String amountsOfRoomCategoriesExpected = "2,0";

        // when ... then
        this.mockMvc.perform(post("/createbooking/service")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "guestId", guestIdExpected,
                                "amountOfAdults", amountOfAdultsExpected,
                                "checkInDate", String.valueOf(checkInDateExpected),
                                "checkOutDate", String.valueOf(checkOutDateExpected),
                                "roomCategoryIds", roomCategoryIdsExoected,
                                "amountsOfRoomCategories", amountsOfRoomCategoriesExpected
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingService"));

        // then
        Mockito.verify(serviceListingService, times(1)).allServices();
    }

    @Test
    public void when_post_createBookingSummaryUrl_then_statusOk_and_bookingSummaryView_and_createSummary_called()
            throws Exception {
        // given
        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";
        String amountOfChildrenExpected = "0";
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIdsExoected = "1,2";
        List<String> roomCategoryIdsExoectedList = List.of("1", "2");
        String amountsOfRoomCategoriesExpected = "2,0";
        List<Integer> amountsOfRoomCategoriesExpectedList = List.of(2, 0);
        String serviceIdsExpected = "1,2";
        List<String> serviceIdsExpectedList = List.of("1", "2");
        String additionalInformationExpected = "Vegan";

        GuestDTO guestExpected = GuestDTO.builder()
                .withId(guestIdExpected)
                .withFirstName("John")
                .withLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("42")
                .withCity("Dornbirn")
                .withZipCode("6850")
                .withCountry("Austria")
                .build();

        RoomCategoryDTO roomCategoryExpected = RoomCategoryDTO.builder()
                .withId("1")
                .withName("Single Room")
                .build();

        Map<RoomCategoryDTO, Integer> categoriesWithAmountsExpected = new HashMap<>();
        categoriesWithAmountsExpected.put(roomCategoryExpected, 1);

        List<ServiceDTO> servicesExpected = List.of(
                ServiceDTO.builder()
                        .withId("1")
                        .withName("TV")
                        .withPrice(new BigDecimal("100"))
                        .build(),
                ServiceDTO.builder()
                        .withId("2")
                        .withName("BreakFast")
                        .withPrice(new BigDecimal("100"))
                        .build()
        );

        BookingSummaryDTO bookingSummaryExpected = BookingSummaryDTO.builder()
                .withGuest(guestExpected)
                .withRoomCategoriesAndAmounts(categoriesWithAmountsExpected)
                .withServices(servicesExpected)
                .withCheckInDate(checkInDateExpected)
                .withCheckOutDate(checkOutDateExpected)
                .withAmountOfAdults(Integer.parseInt(amountOfAdultsExpected))
                .withAmountOfChildren(Integer.parseInt(amountOfChildrenExpected))
                .withAdditionalInformation(additionalInformationExpected)
                .build();

        Mockito.when(bookingSummaryService.createSummary(
                guestIdExpected,
                roomCategoryIdsExoectedList,
                amountsOfRoomCategoriesExpectedList,
                serviceIdsExpectedList,
                checkInDateExpected,
                checkOutDateExpected,
                Integer.parseInt(amountOfAdultsExpected),
                Integer.parseInt(amountOfChildrenExpected),
                additionalInformationExpected
        )).thenReturn(bookingSummaryExpected);

        // when ... then
        this.mockMvc.perform(post("/createbooking/summary")
                .param("isCreated", "false")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "guestId", guestIdExpected,
                        "amountOfAdults", amountOfAdultsExpected,
                        "amountOfChildren", amountOfChildrenExpected,
                        "checkInDate", String.valueOf(checkInDateExpected),
                        "checkOutDate", String.valueOf(checkOutDateExpected),
                        "roomCategoryIds", roomCategoryIdsExoected,
                        "amountsOfRoomCategories", amountsOfRoomCategoriesExpected,
                        "serviceIds", serviceIdsExpected,
                        "additionalInformation", additionalInformationExpected
                )));

        // then
        Mockito.verify(bookingSummaryService, times(1)).createSummary(
                guestIdExpected,
                roomCategoryIdsExoectedList,
                amountsOfRoomCategoriesExpectedList,
                serviceIdsExpectedList,
                checkInDateExpected,
                checkOutDateExpected,
                Integer.parseInt(amountOfAdultsExpected),
                Integer.parseInt(amountOfChildrenExpected),
                additionalInformationExpected
        );
    }

    @Test
    public void when_post_createBookingUrl_then_statusRedirect_and_redirectToBookingSummaryView_and_createBooking_called()
            throws Exception {
        // given
        String bookingIdExpected = "1";
        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";
        String amountOfChildrenExpected = "0";
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIdsExoected = "1,2";
        List<String> roomCategoryIdsExoectedList = List.of("1", "2");
        String amountsOfRoomCategoriesExpected = "2,0";
        List<Integer> amountsOfRoomCategoriesExpectedList = List.of(2, 0);
        String serviceIdsExpected = "1,2";
        List<String> serviceIdsExpectedList = List.of("1", "2");
        String additionalInformationExpected = "Vegan";

        Mockito.when(bookingCreationService.book(
                guestIdExpected,
                roomCategoryIdsExoectedList,
                amountsOfRoomCategoriesExpectedList,
                serviceIdsExpectedList,
                checkInDateExpected,
                checkOutDateExpected,
                Integer.parseInt(amountOfAdultsExpected),
                Integer.parseInt(amountOfChildrenExpected),
                additionalInformationExpected
        )).thenReturn(bookingIdExpected);

        // when ... then
        this.mockMvc.perform(post("/createbooking")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "guestId", guestIdExpected,
                                "amountOfAdults", amountOfAdultsExpected,
                                "amountOfChildren", amountOfChildrenExpected,
                                "checkInDate", String.valueOf(checkInDateExpected),
                                "checkOutDate", String.valueOf(checkOutDateExpected),
                                "roomCategoryIds", roomCategoryIdsExoected,
                                "amountsOfRoomCategories", amountsOfRoomCategoriesExpected,
                                "serviceIds", serviceIdsExpected,
                                "additionalInformation", additionalInformationExpected
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                //.andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(
                        "redirect:/createbookingSuccess?bookingId=" + bookingIdExpected
                                + "&isCreated=true"
                ));

        // then
        Mockito.verify(bookingCreationService, times(1)).book(
                guestIdExpected,
                roomCategoryIdsExoectedList,
                amountsOfRoomCategoriesExpectedList,
                serviceIdsExpectedList,
                checkInDateExpected,
                checkOutDateExpected,
                Integer.parseInt(amountOfAdultsExpected),
                Integer.parseInt(amountOfChildrenExpected),
                additionalInformationExpected
        );
    }

    // TODO: test createBookingSuccess
    @Test
    public void when_get_createBookingSuccessUrl_then_statusOk_and_bookingSummaryView_and_bookingSummaryService_called()
            throws Exception {
        // given
        String bookingIdExpected = "1";

        String guestIdExpected = "1";
        String amountOfAdultsExpected = "2";
        String amountOfChildrenExpected = "0";
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String additionalInformationExpected = "Vegan";

        GuestDTO guestExpected = GuestDTO.builder()
                .withId(guestIdExpected)
                .withFirstName("John")
                .withLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("42")
                .withCity("Dornbirn")
                .withZipCode("6850")
                .withCountry("Austria")
                .build();

        RoomCategoryDTO roomCategoryExpected = RoomCategoryDTO.builder()
                .withId("1")
                .withName("Single Room")
                .build();

        Map<RoomCategoryDTO, Integer> categoriesWithAmountsExpected = new HashMap<>();
        categoriesWithAmountsExpected.put(roomCategoryExpected, 1);

        List<ServiceDTO> servicesExpected = List.of(
                ServiceDTO.builder()
                        .withId("1")
                        .withName("TV")
                        .withPrice(new BigDecimal("100"))
                        .build(),
                ServiceDTO.builder()
                        .withId("2")
                        .withName("BreakFast")
                        .withPrice(new BigDecimal("100"))
                        .build()
        );

        BookingSummaryDTO bookingSummaryExpected = BookingSummaryDTO.builder()
                .withGuest(guestExpected)
                .withRoomCategoriesAndAmounts(categoriesWithAmountsExpected)
                .withServices(servicesExpected)
                .withCheckInDate(checkInDateExpected)
                .withCheckOutDate(checkOutDateExpected)
                .withAmountOfAdults(Integer.parseInt(amountOfAdultsExpected))
                .withAmountOfChildren(Integer.parseInt(amountOfChildrenExpected))
                .withAdditionalInformation(additionalInformationExpected)
                .build();

        Mockito.when(bookingSummaryService.summaryByBookingId(bookingIdExpected)).thenReturn(bookingSummaryExpected);

        // when ... then
        this.mockMvc.perform(get("/createbookingSuccess")
                        .param("bookingId", bookingIdExpected)
                        .param("isCreated", "true")
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("text/html;charset=UTF-8"))
                        .andExpect(view().name("booking/createBookingSummary"));

        // then
        Mockito.verify(bookingSummaryService, times(1)).summaryByBookingId(bookingIdExpected);
    }

    @Test
    public void when_get_bookingDetailsUrl_then_statusOk_and_bookingDetailsView_and_bookingSummaryService_called()
            throws Exception {
        // given
        String bookingId = "1";
        LocalDate checkInDate = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDate = LocalDate.of(2021, 8, 10);

        Map<String, Integer> categoriesWithAmounts = Map.of(
            "Single Room", 1
        );

        Map<String, BigDecimal> services = Map.of(
                "TV", new BigDecimal("100"),
                "BreakFast", new BigDecimal("100")
        );


        // Create BookingDetailsDTO, to mock service which is used by the view controller method
        BookingDetailsDTO bookingDetails = BookingDetailsDTO.builder()
                .withId(bookingId)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withServices(services)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAmountOfAdults(2)
                .withAmountOfChildren(0)
                .withAdditionalInformation("Vegan")
                .build();

        Mockito.when(bookingSummaryService.detailsByBookingId(bookingId)).thenReturn(bookingDetails);

        // when ... then
        this.mockMvc.perform(get("/bookingdetails/" + bookingId)
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/bookingDetails"));

        // then
        Mockito.verify(bookingSummaryService, times(1)).detailsByBookingId(bookingId);
    }

    @Test
    public void when_get_invoicesUrl_then_statusOk_and_invoicesView_and_allInvoices_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/invoices").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/invoices"));

        // then
        Mockito.verify(invoiceListingService, times(1)).allInvoices();
    }

    @Test
    public void when_errorUrl_then_statusOk_and_errorView_called_and_message_displayed() throws Exception {
        // given
        String messageExpected = "test message";

        // when ... then
        this.mockMvc.perform(get("/displayerror")
                .param("message", messageExpected)
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString(messageExpected)))
                .andExpect(view().name("errorView"));
    }

    // Helper Function
    private String buildUrlEncodedFormEntity(String... params) {
        if( (params.length % 2) > 0 ) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0; i<params.length; i+=2) {
            if( i > 0 ) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}
