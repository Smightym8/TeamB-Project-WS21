package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.api.exception.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.view.forms.BookingForm;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

/*----- Invoice Download -----*/
    private static final String INVOICES_PATH = "src/main/resources/static/invoices/";
    private static final String INVOICE_DOWNLOAD_URL = "/download-invoice/{invoiceNo}";

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

    @Autowired
    private InvoiceDownloadService invoiceDownloadService;


/*--------------------------------------------------------------------------------------------------------------------*/

/*----- Home -----*/
    @GetMapping(HOME_URL)
    public String home(Model model) {
        final List<BookingListingDTO> bookings = bookingListingService.allBookings();
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
        final List<BookingListingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return BOOKINGS_VIEW;
    }

/*----- Stays -----*/
    @GetMapping(STAYS_URL)
    public String stays(Model model) {
        final List<StayListingDTO> stays = stayListingService.allStays();

        model.addAttribute("stays", stays);

        return STAYS_VIEW;
    }

/*----- Invoices -----*/
    @GetMapping(INVOICES_URL)
    public String invoices(Model model) {
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
    public ModelAndView createBookingSummary(
            @ModelAttribute("bookingForm") BookingForm bookingForm,
            @RequestParam("isCreated") boolean isCreated,
            Model model) {

        BookingDetailsDTO bookingDetailsDTO;
        try {
            bookingDetailsDTO = bookingSummaryService.createSummary(
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
        } catch (GuestNotFoundException | ServiceNotFoundException | RoomCategoryNotFoundException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("bookingSummary", bookingDetailsDTO);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("isCreated", isCreated);

        return new ModelAndView(CREATE_BOOKING_SUMMARY_VIEW);
    }

    @PostMapping(CREATE_BOOKING_URL)
    public ModelAndView createBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model) {
        String bookingId;
        try {
            bookingId = bookingCreationService.book(bookingForm.getGuestId(),
                    bookingForm.getRoomCategoryIds(),
                    bookingForm.getAmountsOfRoomCategories(),
                    bookingForm.getServiceIds(),
                    bookingForm.getCheckInDate(),
                    bookingForm.getCheckOutDate(),
                    bookingForm.getAmountOfAdults(),
                    bookingForm.getAmountOfChildren(),
                    bookingForm.getAdditionalInformation());
        } catch (GuestNotFoundException | ServiceNotFoundException | RoomCategoryNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView("redirect:" +
                CREATE_BOOKING_SUCCESS_URL +
                "?bookingId=" + bookingId +
                "&isCreated=" + true
        );
    }

    @GetMapping(CREATE_BOOKING_SUCCESS_URL)
    public ModelAndView createBookingSuccess(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("isCreated") boolean isCreated,
            Model model
    ) {
        BookingDetailsDTO bookingDetailsDTO;
        try {
            bookingDetailsDTO = bookingSummaryService.detailsByBookingId(bookingId);
        } catch (BookingNotFoundException | GuestNotFoundException e) {
            return redirectError(e.getMessage());
        }

        List<Integer> amounts = new ArrayList<>(bookingDetailsDTO.categoriesWithAmounts().values());

        BookingForm bookingForm = new BookingForm(
                bookingDetailsDTO.guestId(),
                bookingDetailsDTO.categoryIds(),
                bookingDetailsDTO.serviceIds(),
                bookingDetailsDTO.checkInDate(),
                bookingDetailsDTO.checkOutDate(),
                amounts,
                bookingDetailsDTO.amountOfAdults(),
                bookingDetailsDTO.amountOfChildren()
        );

        model.addAttribute("bookingSummary", bookingDetailsDTO);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("isCreated", isCreated);

        return new ModelAndView(CREATE_BOOKING_SUMMARY_VIEW);
    }

    @GetMapping(BOOKING_DETAILS_URL)
    public ModelAndView showBooking(@PathVariable String id, Model model) {

        BookingDetailsDTO bookingDetailsDTO;
        try {
            bookingDetailsDTO = bookingSummaryService.detailsByBookingId(id);
        } catch (BookingNotFoundException | GuestNotFoundException e) {
            return redirectError(e.getMessage());
        }
        model.addAttribute("bookingDetails", bookingDetailsDTO);

        return new ModelAndView(BOOKING_DETAILS_VIEW);
    }

/*----- Check-In -----*/
    @GetMapping(CHECK_IN_URL)
    public ModelAndView checkIn(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("isCheckIn") boolean isCheckIn,
            Model model) {

        List<RoomDTO> assignedRooms;
        try {
            assignedRooms = checkInService.assignRooms(bookingId);
        } catch (BookingNotFoundException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("bookingId", bookingId);
        model.addAttribute("assignedRooms", assignedRooms);
        model.addAttribute("isCheckIn", isCheckIn);

        return new ModelAndView(CHECK_IN_VIEW);
    }

/*----- Check-Out -----*/
    @GetMapping(STAY_DETAILS_URL)
    public ModelAndView showStay(@PathVariable String id, Model model) {
        StayDetailsDTO stayDetailsDTO;

        try {
            stayDetailsDTO = stayDetailsService.detailsById(id);
            model.addAttribute("stayDetails", stayDetailsDTO);
        } catch (StayNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView(STAY_DETAILS_VIEW);
    }

    @GetMapping(INVOICE_URL)
    public ModelAndView showInvoice(@PathVariable String id, Model model) {
        InvoiceDTO invoiceDTO;

        try {
            invoiceDTO = checkOutService.createInvoice(id);
        } catch (StayNotFoundException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("invoice", invoiceDTO);

        return new ModelAndView(INVOICE_VIEW);
    }

    // TODO: Test
    @GetMapping(CHECK_OUT_URL)
    public ModelAndView checkOut(@RequestParam("stayId") String stayId) {
        try {
            checkOutService.checkOut(stayId);
        } catch (StayNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView("redirect:" + HOME_URL);
    }

/*----- Invoice Download -----*/
    @GetMapping(INVOICE_DOWNLOAD_URL)
    public ResponseEntity<ByteArrayResource> downloadInvoice(@PathVariable("invoiceNo") String invoiceNo) {
        ByteArrayResource resource = null;

        try {
            resource = invoiceDownloadService.download(invoiceNo);
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace(); // TODO: Return to errorView --> return type mismatch
        }

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Invoice_" + invoiceNo + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                // Content-Length
                .contentLength(resource.contentLength())
                .body(resource);
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
