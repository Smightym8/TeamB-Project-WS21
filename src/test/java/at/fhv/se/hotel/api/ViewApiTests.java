package at.fhv.se.hotel.api;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// TODO: Test try catch from HotelViewController
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
    CheckInService checkInService;

    @MockBean
    StayDetailsService stayDetailsService;

    @MockBean
    CheckOutService checkOutService;

    @MockBean
    InvoiceListingService invoiceListingService;

    @MockBean
    InvoiceDownloadService invoiceDownloadService;

    @MockBean
    RoomListingService roomListingService;

    @MockBean
    RoomModifyService roomModifyService;

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
    public void when_get_roomsUrl_then_statusOk_and_roomsView_and_roomListingService_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/rooms").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/rooms"));

        // then
        Mockito.verify(roomListingService, times(1)).allRooms();
    }

    @Test
    public void when_get_roomUrl_then_statusOk_and_modifyRoomView_and_roomListingService_called() throws Exception {
        // given
        String roomName = "100";
        RoomDTO roomDTO = RoomDTO.builder()
                .withName(roomName)
                .withStatus("Free")
                .withCategory("Single Room")
                .build();

        Mockito.when(roomListingService.roomByName(roomName)).thenReturn(roomDTO);

        // when
        this.mockMvc.perform(get("/room/" + roomName).accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("modifyRoom"));

        // then
        Mockito.verify(roomListingService, times(1)).roomByName(roomName);
    }

    @Test
    public void when_post_modifyRoomUrl_then_statusRedirect_and_redirectToRoomsView_and_roomModifyService_called() throws Exception {
        // given
        String roomName = "100";
        String roomStatus = "Free";
        String roomCategory = "Single Room";

        Mockito.doNothing().when(roomModifyService).modifyStatus(roomName, roomStatus);

        // when
        this.mockMvc.perform(post("/modifyRoom")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "roomName", roomName,
                                "roomStatus", roomStatus,
                                "roomCategory", roomCategory
                        ))
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rooms"));

        // then
        Mockito.verify(roomModifyService, times(1)).modifyStatus(roomName, roomStatus);
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
        int discountInPercentExpected = 0;

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
                        "country", countryExpected,
                        "discountInPercent", String.valueOf(discountInPercentExpected)
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
                        countryExpected,
                        discountInPercentExpected
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
                .andExpect(status().isOk())
                .andExpect(view().name("createGuest"));
    }

    @Test
    public void when_get_createBookingDateUrl_then_statusOk_and_createBookingDateView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/createbooking/date")
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
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);

        // when ... then
        this.mockMvc.perform(post("/createbooking/category")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
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
    public void when_post_createBookingServiceUrlAndParamNext_then_statusOk_and_createBookingServiceView_and_allServices_called()
            throws Exception {
        // given
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIdsExpected = "1,2";
        String amountsOfRoomCategoriesExpected = "2,0";

        // when ... then
        this.mockMvc.perform(post("/createbooking/service")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "checkInDate", String.valueOf(checkInDateExpected),
                                "checkOutDate", String.valueOf(checkOutDateExpected),
                                "roomCategoryIds", roomCategoryIdsExpected,
                                "amountsOfRoomCategories", amountsOfRoomCategoriesExpected
                        ))
                        .param("action", "next")
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingService"));

        // then
        Mockito.verify(serviceListingService, times(1)).allServices();
    }

    @Test
    public void when_post_createBookingServiceUrlAndParamBack_then_statusOk_and_createBookingServiceView_and_allServices_called()
            throws Exception {
        // given
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIdsExpected = "1,2";
        String amountsOfRoomCategoriesExpected = "2,0";

        // when ... then
        this.mockMvc.perform(post("/createbooking/service")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "checkInDate", String.valueOf(checkInDateExpected),
                        "checkOutDate", String.valueOf(checkOutDateExpected),
                        "roomCategoryIds", roomCategoryIdsExpected,
                        "amountsOfRoomCategories", amountsOfRoomCategoriesExpected
                ))
                .param("action", "back")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingDate"));
    }

    @Test
    public void when_post_createBookingGuestUrlAndParamNext_then_statusOk_and_createBookingGuestView_and_allGuests_called()
            throws Exception {
        // given
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIds = "1,2";
        String amountsOfRoomCategories = "2,0";
        String serviceIds = "1,2";
        String additionalInformation = "Vegan";

        // when ... then
        this.mockMvc.perform(post("/createbooking/guest")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity(
                                "checkInDate", String.valueOf(checkInDateExpected),
                                "checkOutDate", String.valueOf(checkOutDateExpected),
                                "roomCategoryIds", roomCategoryIds,
                                "amountsOfRoomCategories", amountsOfRoomCategories,
                                "serviceIds", serviceIds,
                                "additionalInformation", additionalInformation
                        ))
                        .param("action", "next")
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingGuest"));

        // then
        Mockito.verify(guestListingService, times(1)).allGuests();
    }

    @Test
    public void when_post_createBookingGuestUrlAndParamBack_then_statusOk_and_createBookingGuestView_and_allGuests_called()
            throws Exception {
        // given
        LocalDate checkInDateExpected = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2021, 8, 10);
        String roomCategoryIds = "1,2";
        String amountsOfRoomCategories = "2,0";
        String serviceIds = "1,2";
        String additionalInformation = "Vegan";

        // when ... then
        this.mockMvc.perform(post("/createbooking/guest")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "checkInDate", String.valueOf(checkInDateExpected),
                        "checkOutDate", String.valueOf(checkOutDateExpected),
                        "roomCategoryIds", roomCategoryIds,
                        "amountsOfRoomCategories", amountsOfRoomCategories,
                        "serviceIds", serviceIds,
                        "additionalInformation", additionalInformation
                ))
                .param("action", "back")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("booking/createBookingCategory"));

        // then
        Mockito.verify(roomCategoryListingService, times(1)).allRoomCategories();
    }

    @Test
    public void when_post_createBookingSummaryUrlAndParamNext_then_statusOk_and_bookingSummaryView_and_createSummary_called()
            throws Exception {
        // given
        String guestId = "1";
        String amountOfAdults = "2";
        String amountOfChildren = "0";
        LocalDate checkInDate = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDate = LocalDate.of(2021, 8, 10);
        String roomCategoryIds = "1,2";
        List<String> roomCategoryIdsList = List.of("1", "2");
        String amountsOfRoomCategories = "2,0";
        List<Integer> amountsOfRoomCategoriesList = List.of(2, 0);
        String serviceIds = "1,2";
        List<String> serviceIdsList = List.of("1", "2");
        String additionalInformation = "Vegan";

        Map<String, Integer> categoriesWithAmounts = Map.of(
                "Single Room", 1
        );

        Map<String, BigDecimal> services = Map.of(
                "TV", new BigDecimal("100"),
                "Breakfast", new BigDecimal("100")
        );

        BookingDetailsDTO bookingSummaryExpected = BookingDetailsDTO.builder()
                .withGuestId(guestId)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("42")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withCountry("Austria")
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withServices(services)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAdditionalInformation(additionalInformation)
                .withAmountOfAdults(Integer.parseInt(amountOfAdults))
                .withAmountOfChildren(Integer.parseInt(amountOfChildren))
                .build();

        Mockito.when(bookingSummaryService.createSummary(
                guestId,
                roomCategoryIdsList,
                amountsOfRoomCategoriesList,
                serviceIdsList,
                checkInDate,
                checkOutDate,
                Integer.parseInt(amountOfAdults),
                Integer.parseInt(amountOfChildren),
                additionalInformation
        )).thenReturn(bookingSummaryExpected);

        // when ... then
        this.mockMvc.perform(post("/createbooking/summary")
                .param("isCreated", "false")
                .param("action", "next")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "guestId", guestId,
                        "amountOfAdults", amountOfAdults,
                        "amountOfChildren", amountOfChildren,
                        "checkInDate", String.valueOf(checkInDate),
                        "checkOutDate", String.valueOf(checkOutDate),
                        "roomCategoryIds", roomCategoryIds,
                        "amountsOfRoomCategories", amountsOfRoomCategories,
                        "serviceIds", serviceIds,
                        "additionalInformation", additionalInformation
                )));

        // then
        Mockito.verify(bookingSummaryService, times(1)).createSummary(
                guestId,
                roomCategoryIdsList,
                amountsOfRoomCategoriesList,
                serviceIdsList,
                checkInDate,
                checkOutDate,
                Integer.parseInt(amountOfAdults),
                Integer.parseInt(amountOfChildren),
                additionalInformation
        );
    }

    @Test
    public void when_post_createBookingSummaryUrlAndParamBack_then_statusOk_and_bookingSummaryView_and_createSummary_called()
            throws Exception {
        // given
        String guestId = "1";
        String amountOfAdults = "2";
        String amountOfChildren = "0";
        LocalDate checkInDate = LocalDate.of(2021, 8, 1);
        LocalDate checkOutDate = LocalDate.of(2021, 8, 10);
        String roomCategoryIds = "1,2";
        String amountsOfRoomCategories = "2,0";
        String serviceIds = "1,2";
        String additionalInformation = "Vegan";

        // when ... then
        this.mockMvc.perform(post("/createbooking/summary")
                .param("isCreated", "false")
                .param("action", "back")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "guestId", guestId,
                        "amountOfAdults", amountOfAdults,
                        "amountOfChildren", amountOfChildren,
                        "checkInDate", String.valueOf(checkInDate),
                        "checkOutDate", String.valueOf(checkOutDate),
                        "roomCategoryIds", roomCategoryIds,
                        "amountsOfRoomCategories", amountsOfRoomCategories,
                        "serviceIds", serviceIds,
                        "additionalInformation", additionalInformation
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

    @Test
    public void when_get_createBookingSuccessUrl_then_statusOk_and_bookingSummaryView_and_bookingSummaryService_called()
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
                "Breakfast", new BigDecimal("100")
        );

        BookingDetailsDTO bookingDetailsDTO = BookingDetailsDTO.builder()
                .withBookingId(bookingId)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("42")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withCountry("Austria")
                .withRoomCategoriesAndAmounts(categoriesWithAmounts)
                .withCategoryIds(List.of("1"))
                .withServices(services)
                .withServiceIds(List.of("1", "2"))
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .withAdditionalInformation("Nothing to say")
                .withAmountOfAdults(2)
                .withAmountOfChildren(2)
                .build();

        Mockito.when(bookingSummaryService.detailsByBookingId(bookingId)).thenReturn(bookingDetailsDTO);

        // when ... then
        this.mockMvc.perform(get("/createbookingSuccess")
                        .param("bookingId", bookingId)
                        .param("isCreated", "true")
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("text/html;charset=UTF-8"))
                        .andExpect(view().name("booking/createBookingSummary"));

        // then
        Mockito.verify(bookingSummaryService, times(1)).detailsByBookingId(bookingId);
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
                .withBookingId(bookingId)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("42")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withCountry("Austria")
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
    public void when_get_checkInUrl_with_isCheckedInFalse_then_statusOk_and_checkInView_and_checkInService_called() throws Exception {
        // given
        String bookingIdExpected = "1";
        String isCheckedInExpected = "false";

        List<RoomDTO> roomDTOsExpected = List.of(
                RoomDTO.builder()
                        .withName("101")
                        .withCategory("Single Room")
                        .withStatus(RoomStatus.FREE.name())
                        .build()
        );

        Mockito.when(checkInService.assignRooms(bookingIdExpected)).thenReturn(roomDTOsExpected);

        // when ... then
        this.mockMvc.perform(get("/check-in")
                .param("bookingId", bookingIdExpected)
                .param("isCheckedIn", isCheckedInExpected)
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("checkIn"));

        // then
        Mockito.verify(checkInService, times(1)).assignRooms(bookingIdExpected);
        Mockito.verify(checkInService, times(0)).checkIn(bookingIdExpected, roomDTOsExpected);
    }

    @Test
    public void when_get_checkInUrl_with_isCheckedInTrue_then_statusOk_and_checkInView_and_checkInService_called() throws Exception {
        // given
        String bookingIdExpected = "1";
        String isCheckedInExpected = "true";

        List<RoomDTO> roomDTOsExpected = List.of(
                RoomDTO.builder()
                        .withName("101")
                        .withCategory("Single Room")
                        .withStatus(RoomStatus.FREE.name())
                        .build()
        );

        Mockito.when(checkInService.assignRooms(bookingIdExpected)).thenReturn(roomDTOsExpected);
        Mockito.doNothing().when(checkInService).checkIn(bookingIdExpected, roomDTOsExpected);

        // when ... then
        this.mockMvc.perform(get("/check-in")
                        .param("bookingId", bookingIdExpected)
                        .param("isCheckedIn", isCheckedInExpected)
                        .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("checkIn"));

        // then
        Mockito.verify(checkInService, times(1)).assignRooms(bookingIdExpected);
    }

    @Test
    public void when_get_stayDetailsUrl_then_statusOk_and_stayDetailsView_and_stayDetailsService_called() throws Exception {
        // given
        String stayIdExpected = "1";
        StayDetailsDTO stayDetailsDTOExpected = StayDetailsDTO.builder()
                .withId(stayIdExpected)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("1")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withCountry("Austria")
                .withRoomsWithCategories(Map.of("101", "Single Room"))
                .withServices(Map.of("", new BigDecimal("30")))
                .withCheckInDate(LocalDate.of(2021, 8, 1))
                .withCheckOutDate(LocalDate.of(2021, 8, 10))
                .withAmountOfAdults(2)
                .withAmountOfChildren(1)
                .withAdditionalInformation("Vegan")
                .build();

        Mockito.when(stayDetailsService.detailsById(stayIdExpected)).thenReturn(stayDetailsDTOExpected);

        // when ... then
        this.mockMvc.perform(get("/staydetails/" + stayIdExpected)
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("stay/stayDetails"));

        // then
        Mockito.verify(stayDetailsService, times(1)).detailsById(stayIdExpected);
    }

    @Test
    public void when_get_invoiceUrl_and_createInvoice_then_statusOk_and_invoiceView_and_checkOutService_called() throws Exception {
        // given
        String stayIdExpected = "1";
        StayDetailsDTO stayDetailsDTOExpected = StayDetailsDTO.builder()
                .withId(stayIdExpected)
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("1")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withCountry("Austria")
                .withRoomsWithCategories(Map.of("101", "Single Room"))
                .withServices(Map.of("", new BigDecimal("30")))
                .withCheckInDate(LocalDate.of(2021, 8, 1))
                .withCheckOutDate(LocalDate.of(2021, 8, 10))
                .withAmountOfAdults(2)
                .withAmountOfChildren(1)
                .withAdditionalInformation("Vegan")
                .build();

        InvoiceDTO invoiceDTOExpected = InvoiceDTO.builder()
                .withStayId(stayIdExpected)
                .withInvoiceNumber("20211220001")
                .withInvoiceDate(LocalDate.of(2021, 12, 20))
                .withGuestFirstName(stayDetailsDTOExpected.guestFirstName())
                .withGuestLastName(stayDetailsDTOExpected.guestLastName())
                .withStreetName("Street")
                .withStreetNumber("1")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withAmountOfAdults(2)
                .withAmountOfChildren(1)
                .withServices(Map.of("Breakfast", new BigDecimal("100")))
                .withRoomNames(List.of("101"))
                .withCheckInDate(stayDetailsDTOExpected.checkInDate())
                .withCheckOutDate(stayDetailsDTOExpected.checkOutDate())
                .withAmountOfNights(9)
                .withLocalTaxPerPerson(new BigDecimal("0.52"))
                .withLocalTaxTotal(new BigDecimal("0.52"))
                .withValueAddedTaxInPercent(new BigDecimal("0.1"))
                .withValueAddedTaxInEuro(new BigDecimal("0.52"))
                .withTotalNetAmountBeforeDiscount(new BigDecimal("0.52"))
                .withTotalGrossAmount(new BigDecimal("0.52"))
                .withDiscountInPercent(10)
                .withDiscountInEuro(new BigDecimal("0.52"))
                .withCategoryNames(List.of("Single Room"))
                .withCategoryPrices(List.of(new BigDecimal("0.52")))
                .withTotalNetAmountAfterDiscount(new BigDecimal("200"))
                .withTotalNetAmountAfterLocalTax(new BigDecimal("200"))
                .build();

        List<String> roomNamesExpected = List.of("101");

        String action = "createInvoice";

        Mockito.when(checkOutService.createInvoice(stayIdExpected, roomNamesExpected, action)).thenReturn(invoiceDTOExpected);
        Mockito.when(stayDetailsService.detailsById(stayIdExpected)).thenReturn(stayDetailsDTOExpected);

        // when ... then
        this.mockMvc.perform(get("/invoice/" + stayIdExpected)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "roomNames", "101"
                ))
                .param("action", "createInvoice")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("invoice"));

        // then
        Mockito.verify(checkOutService, times(1)).createInvoice(stayIdExpected, roomNamesExpected, action);
        Mockito.verify(stayDetailsService, times(1)).detailsById(stayIdExpected);
    }

    @Test
    public void when_get_saveInvoiceUrl_and_createInvoice_then_statusRedirect_and_redirectToStayDetailsView_and_checkOutService_called() throws Exception{
        // given
        String stayIdExpected = "1";
        List<String> roomNamesExpected = List.of("101");

        String action = "createInvoice";
        Mockito.doNothing().when(checkOutService).saveInvoice(stayIdExpected, roomNamesExpected, action);

        // when ... then
        this.mockMvc.perform(get("/saveinvoice/" + stayIdExpected)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "roomNames", "101"
                ))
                .param("action", "createInvoice")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/staydetails/{id}"));

        // then
        Mockito.verify(checkOutService, times(1)).saveInvoice(stayIdExpected, roomNamesExpected, action);
    }

    @Test
    public void when_get_saveInvoiceUrl_and_checkOut_then_statusRedirect_and_redirectToHomeView_and_checkOutService_called() throws Exception{
        // given
        String stayIdExpected = "1";
        List<String> roomNamesExpected = List.of("101");
        String action = "checkOut";

        Mockito.doNothing().when(checkOutService).checkOut(stayIdExpected, roomNamesExpected, action);

        // when ... then
        this.mockMvc.perform(get("/saveinvoice/" + stayIdExpected)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "roomNames", "101"
                ))
                .param("action", "checkOut")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        // then
        Mockito.verify(checkOutService, times(1)).checkOut(stayIdExpected, roomNamesExpected, action);
    }

    @Test
    public void when_invoiceDownloadUrl_then_statusOk_and_invoiceDownloadService_called() throws Exception {
        // given
        String invoiceNo = "20211220";
        // Use String as fake byte array
        ByteArrayResource byteArrayResource = new ByteArrayResource(invoiceNo.getBytes(StandardCharsets.UTF_8));

        Mockito.when(invoiceDownloadService.download(invoiceNo)).thenReturn(byteArrayResource);

        // when
        this.mockMvc.perform(get("/download-invoice/" + invoiceNo)
                .accept(org.springframework.http.MediaType.APPLICATION_PDF))
                .andExpect(status().isOk());

        // then
        Mockito.verify(invoiceDownloadService, times(1)).download(invoiceNo);
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