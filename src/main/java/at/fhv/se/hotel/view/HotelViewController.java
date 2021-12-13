package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.application.impl.InvoiceListingServiceImpl;
import at.fhv.se.hotel.view.forms.BookingForm;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HotelViewController {

/* ----- Sidebar ----- */
    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "sidebar/home";

    private static final String ROOMS_URL = "/rooms";
    private static final String ROOMS_VIEW = "sidebar/rooms";

    private static final String PRICING_URL = "/pricing";
    private static final String PRICING_VIEW = "sidebar/pricing";

    private static final String GUESTS_URL = "/guests";
    private static final String GUESTS_VIEW = "sidebar/guests";

    private static final String BOOKINGS_URL = "/bookings";
    private static final String BOOKINGS_VIEW = "sidebar/bookings";

    private static final String STAYS_URL = "/stays";
    private static final String STAYS_VIEW = "sidebar/stays";

    private static final String INVOICES_URL = "/invoices";
    private static final String INVOICES_VIEW = "sidebar/invoices";

/*----- Create Guest -----*/
    private static final String CREATE_GUEST_URL = "/createguest";
    private static final String CREATE_GUEST_VIEW = "createGuest";

/*----- Create Booking -----*/
    private static final String CREATE_BOOKING_GUEST_URL = "/createbooking/guest";
    private static final String CREATE_BOOKING_GUEST_VIEW = "booking/createBookingGuest";

    private static final String CREATE_BOOKING_DATE_URL = "/createbooking/date";
    private static final String CREATE_BOOKING_DATE_VIEW = "booking/createBookingDate";

    private static final String CREATE_BOOKING_CATEGORY_URL = "/createbooking/category";
    private static final String CREATE_BOOKING_CATEGORY_VIEW = "booking/createBookingCategory";

    private static final String CREATE_BOOKING_SERVICE_URL = "/createbooking/service";
    private static final String CREATE_BOOKING_SERVICE_VIEW = "booking/createBookingService";

    private static final String CREATE_BOOKING_SUMMARY_URL = "/createbooking/summary";
    private static final String CREATE_BOOKING_SUMMARY_VIEW = "booking/createBookingSummary";

    private static final String CREATE_BOOKING_URL = "/createbooking";
    private static final String CREATE_BOOKING_SUCCESS_URL = "/createbookingSuccess";

/*----- Check-In -----*/
    private static final String BOOKING_DETAILS_URL = "/bookingdetails/{id}";
    private static final String BOOKING_DETAILS_VIEW = "booking/bookingDetails";

    private static final String CHECK_IN_URL = "/check-in";
    private static final String CHECK_IN_VIEW = "checkIn";

/*----- Check-Out -----*/
    private static final String STAY_DETAILS_URL = "/staydetails/{id}";
    private static final String STAY_DETAILS_VIEW = "stay/stayDetails";

    private static final String INVOICE_URL = "/invoice/{id}";
    private static final String INVOICE_VIEW = "invoice";

    private static final String CHECK_OUT_URL = "/check-out";

/*----- Error -----*/
    private static final String ERROR_URL = "/displayerror";
    private static final String ERROR_VIEW = "errorView";

/*--------------------------------------------------------------------------------------------------------------------*/

    @Autowired
    private BookingListingService bookingListingService;

    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

    @Autowired
    private ServiceListingService serviceListingService;

    @Autowired
    private BookingSummaryService bookingSummaryService;

    @Autowired
    private BookingCreationService bookingCreationService;

    @Autowired
    private GuestCreationService guestCreationService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private StayListingService stayListingService;

    @Autowired
    private StayDetailsService stayDetailsService;

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private InvoiceListingService invoiceListingService;

/*--------------------------------------------------------------------------------------------------------------------*/

/*----- Home -----*/
    @GetMapping(HOME_URL)
    public String home(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();
        final List<StayListingDTO> stays = stayListingService.allStays();

        model.addAttribute("bookings", bookings);
        model.addAttribute("stays", stays);

        return HOME_VIEW;
    }

/*----- Rooms -----*/
    @GetMapping(ROOMS_URL)
    public String rooms(Model model) {

        return ROOMS_VIEW;
    }

/*----- Pricing -----*/
    @GetMapping(PRICING_URL)
    public String pricing(Model model) {

        return PRICING_VIEW;
    }

/*----- Guests -----*/
    @GetMapping(GUESTS_URL)
    public String guests(Model model) {
        final List<GuestDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);

        return GUESTS_VIEW;
    }

/*----- Bookings -----*/
    @GetMapping(BOOKINGS_URL)
    public String bookings(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return BOOKINGS_VIEW;
    }

/*----- Stays -----*/
    @GetMapping(STAYS_URL)
    public String stays(Model model) {
        // Hibernate shows error if there are no bookings?
        final List<BookingDTO> bookings = bookingListingService.allBookings();
        final List<StayListingDTO> stays = stayListingService.allStays();

        model.addAttribute("bookings", bookings);
        model.addAttribute("stays", stays);

        return STAYS_VIEW;
    }

/*----- Invoices -----*/
    //ToDo: status(isPaid) an die View weitergeben
    @GetMapping(INVOICES_URL)
    public String invoices(Model model) {
        //Error! HHH000143: Bytecode enhancement failed because no public,
        //protected or package-private default constructor was found for entity:
        //at.fhv.se.hotel.domain.model.booking.Booking. Private constructors don't work with runtime proxies!
        final List<BookingDTO> bookings = bookingListingService.allBookings();
        final List<InvoiceListingDTO> invoices = invoiceListingService.allInvoices();

        model.addAttribute("invoices", invoices);

        return INVOICES_VIEW;
    }

/*----- Create Guest -----*/
    @GetMapping(CREATE_GUEST_URL)
    public String createGuestGet(Model model) {
        GuestForm guestForm = new GuestForm();

        model.addAttribute("guest", guestForm);

        return CREATE_GUEST_VIEW;
    }

    @PostMapping(CREATE_GUEST_URL)
    public String createGuestPost(@ModelAttribute("guest") @Valid GuestForm guestForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_GUEST_VIEW;
        }

        guestCreationService.createGuest(
                guestForm.getFirstName(),
                guestForm.getLastName(),
                guestForm.getGender(),
                guestForm.geteMail(),
                guestForm.getPhoneNumber(),
                guestForm.getBirthDate(),
                guestForm.getStreetName(),
                guestForm.getStreetNumber(),
                guestForm.getZipCode(),
                guestForm.getCity(),
                guestForm.getCountry()
        );

        return "redirect:" + GUESTS_URL;
    }

/*----- Create Booking -----*/
    @GetMapping(CREATE_BOOKING_GUEST_URL)
    public String createBookingGuest(Model model) {
        final List<GuestDTO> guests = guestListingService.allGuests();
        BookingForm bookingForm = new BookingForm();

        model.addAttribute("guests", guests);
        model.addAttribute("bookingForm", bookingForm);

        return CREATE_BOOKING_GUEST_VIEW;
    }

    @PostMapping(CREATE_BOOKING_DATE_URL)
    public String createBookingDate(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model) {

        model.addAttribute("bookingForm", bookingForm);

        return CREATE_BOOKING_DATE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_CATEGORY_URL)
    public String createBookingCategory(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("booking", bookingForm);
        model.addAttribute("categories", categories);

        return CREATE_BOOKING_CATEGORY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_SERVICE_URL)
    public String createBookingService(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model) {
        final List<ServiceDTO> services = serviceListingService.allServices();

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("services", services);

        return CREATE_BOOKING_SERVICE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_SUMMARY_URL)
    public String createBookingSummary(
            @ModelAttribute("bookingForm") BookingForm bookingForm,
            @RequestParam("isCreated") boolean isCreated,
            Model model) {

        BookingSummaryDTO bookingSummaryDTO = bookingSummaryService.createSummary(
                bookingForm.getGuestId(),
                bookingForm.getRoomCategoryIds(),
                bookingForm.getAmountsOfRoomCategories(),
                bookingForm.getServiceIds(),
                bookingForm.getCheckInDate(),
                bookingForm.getCheckOutDate(),
                bookingForm.getAmountOfAdults(),
                bookingForm.getAmountOfChildren(),
                bookingForm.getAdditionalInformation()
        );

        model.addAttribute("bookingSummary", bookingSummaryDTO);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("isCreated", isCreated);

        return CREATE_BOOKING_SUMMARY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model) {

        String bookingId = bookingCreationService.book(
                bookingForm.getGuestId(),
                bookingForm.getRoomCategoryIds(),
                bookingForm.getAmountsOfRoomCategories(),
                bookingForm.getServiceIds(),
                bookingForm.getCheckInDate(),
                bookingForm.getCheckOutDate(),
                bookingForm.getAmountOfAdults(),
                bookingForm.getAmountOfChildren(),
                bookingForm.getAdditionalInformation());

        return "redirect:" + CREATE_BOOKING_SUCCESS_URL
                + "?bookingId=" + bookingId + "&isCreated=" + true;
    }

    @GetMapping(CREATE_BOOKING_SUCCESS_URL)
    public String createBookingSuccess(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("isCreated") boolean isCreated,
            Model model
    ) {
        BookingSummaryDTO bookingSummaryDTO = bookingSummaryService.summaryByBookingId(bookingId);
        List<String> roomCategoryIds = new ArrayList<>();


        bookingSummaryDTO.categoriesWithAmounts().keySet().forEach(
                key -> roomCategoryIds.add(key.id())
        );

        List<Integer> amounts = new ArrayList<>(bookingSummaryDTO.categoriesWithAmounts().values());

        List<String> serviceIds = new ArrayList<>();
        bookingSummaryDTO.services().forEach(
                serviceDTO -> serviceIds.add(serviceDTO.id())
        );

        BookingForm bookingForm = new BookingForm(
                bookingSummaryDTO.guest().id(),
                roomCategoryIds,
                serviceIds,
                bookingSummaryDTO.checkInDate(),
                bookingSummaryDTO.checkOutDate(),
                amounts,
                bookingSummaryDTO.amountOfAdults(),
                bookingSummaryDTO.amountOfChildren()
        );

        model.addAttribute("bookingSummary", bookingSummaryDTO);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("isCreated", isCreated);

        return CREATE_BOOKING_SUMMARY_VIEW;
    }

    @GetMapping(BOOKING_DETAILS_URL)
    public String showBooking(@PathVariable String id, Model model) {

        BookingDetailsDTO bookingDetailsDTO =  bookingSummaryService.detailsByBookingId(id);
        model.addAttribute("bookingDetails", bookingDetailsDTO);

        return BOOKING_DETAILS_VIEW;
    }

/*----- Check-In -----*/
    // TODO: Test
    @GetMapping(CHECK_IN_URL)
    public String checkIn(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("isCheckedIn") boolean isCheckedIn,
            Model model) {

        List<RoomDTO> assignedRooms = checkInService.assignRooms(bookingId);

        if(isCheckedIn) {
            checkInService.checkIn(bookingId, assignedRooms);
        }

        model.addAttribute("bookingId", bookingId);
        model.addAttribute("assignedRooms", assignedRooms);
        model.addAttribute("isCheckedIn", isCheckedIn);

        return CHECK_IN_VIEW;
    }

/*----- Check-Out -----*/
    // TODO: Test
    @GetMapping(STAY_DETAILS_URL)
    public String showStay(@PathVariable String id, Model model) {

        // Error! org.hibernate.HibernateException:
        // HHH000143: Bytecode enhancement failed because no public, protected or package-private default constructor
        // was found for entity: at.fhv.se.hotel.domain.model.booking.Booking.
        // Private constructors don't work with runtime proxies!
        BookingDetailsDTO bookingDetailsDTO =  bookingSummaryService.detailsByBookingId(id);
        StayDetailsDTO stayDetailsDTO =  stayDetailsService.detailsById(id);
        model.addAttribute("stayDetails", stayDetailsDTO);

        return STAY_DETAILS_VIEW;
    }

    // TODO: Test
    @GetMapping(INVOICE_URL)
    public String showInvoice(@PathVariable String id, Model model) {

        // Error! org.hibernate.HibernateException:
        // HHH000143: Bytecode enhancement failed because no public, protected or package-private default constructor
        // was found for entity: at.fhv.se.hotel.domain.model.booking.Booking.
        // Private constructors don't work with runtime proxies!
        BookingDetailsDTO bookingDetailsDTO =  bookingSummaryService.detailsByBookingId(id);
        InvoiceDTO invoiceDTO = checkOutService.createInvoice(id);
        model.addAttribute("invoice", invoiceDTO);

        return INVOICE_VIEW;
    }

    // TODO: Test
    @GetMapping(CHECK_OUT_URL)
    public String checkOut(@RequestParam("stayId") String stayId) {

        // Error! org.hibernate.HibernateException:
        // HHH000143: Bytecode enhancement failed because no public, protected or package-private default constructor
        // was found for entity: at.fhv.se.hotel.domain.model.booking.Booking.
        // Private constructors don't work with runtime proxies!
        BookingDetailsDTO bookingDetailsDTO =  bookingSummaryService.detailsByBookingId(stayId);
        checkOutService.checkOut(stayId);

        return "redirect:" + HOME_URL;
    }

/*----- Error -----*/
    @GetMapping(ERROR_URL)
    public ModelAndView displayError(@RequestParam("message") String message, Model model){
        model.addAttribute("message", message);
        return new ModelAndView(ERROR_VIEW);
    }

    // NOTE: used to redirect to an error page displaying an error message
    @SuppressWarnings("unused")
    private static ModelAndView redirectError(String message) {
        return new ModelAndView("redirect:" + ERROR_URL + "?message=" + message);
    }
}
