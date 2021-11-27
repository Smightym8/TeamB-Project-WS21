package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.view.forms.BookingForm;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HotelViewController {

    /* -- Main -- */
    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String ROOMS_URL = "/rooms";
    private static final String ROOMS_VIEW = "rooms";

    private static final String PRICING_URL = "/pricing";
    private static final String PRICING_VIEW = "pricing";

    private static final String GUESTS_URL = "/guests";
    private static final String GUESTS_VIEW = "guests";

    private static final String BOOKINGS_URL = "/bookings";
    private static final String BOOKINGS_VIEW = "bookings";

    private static final String STAYS_URL = "/stays";
    private static final String STAYS_VIEW = "stays";

    private static final String INVOICES_URL = "/invoices";
    private static final String INVOICES_VIEW = "invoices";

    /* -- Create Guest -- */
    private static final String CREATE_GUEST_URL = "/createguest";
    private static final String CREATE_GUEST_VIEW = "createGuest";

    /* -- Create Booking -- */
    private static final String CREATE_BOOKING_GUEST_URL = "/createbooking/guest";
    private static final String CREATE_BOOKING_GUEST_VIEW = "createBookingGuest";

    private static final String CREATE_BOOKING_DATE_URL = "/createbooking/date";
    private static final String CREATE_BOOKING_DATE_VIEW = "createBookingDate";

    private static final String CREATE_BOOKING_CATEGORY_URL = "/createbooking/category";
    private static final String CREATE_BOOKING_CATEGORY_VIEW = "createBookingCategory";

    private static final String CREATE_BOOKING_SERVICE_URL = "/createbooking/service";
    private static final String CREATE_BOOKING_SERVICE_VIEW = "createBookingService";

    private static final String CREATE_BOOKING_SUMMARY_URL = "/createbooking/summary";
    private static final String CREATE_BOOKING_SUMMARY_VIEW = "createBookingSummary";

    private static final String CREATE_BOOKING_URL = "/createbooking";



    private static final String SHOW_BOOKING_URL = "/showbooking/{id}";
    private static final String SHOW_BOOKING_VIEW = "showBooking";


    private static final String CHECK_IN_URL = "/check-in";
    private static final String CHECK_IN_VIEW = "checkIn";

    /* -- Check-In -- */








    private static final String ERROR_URL = "/displayerror";

    private static final String ERROR_VIEW = "errorView";




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
    private BookingDetailsService bookingDetailsService;

    @Autowired
    private GuestCreationService guestCreationService;

    @Autowired
    private CheckInService checkInService;

    /*---------*/

//  ---Home---
    @GetMapping(HOME_URL)
    public String home(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return HOME_VIEW;
    }

//  ---Rooms---
    @GetMapping(ROOMS_URL)
    public String rooms(Model model) {

        return ROOMS_VIEW;
    }

    //  ---Stays---
    @GetMapping(PRICING_URL)
    public String pricing(Model model) {

        return PRICING_VIEW;
    }


//  ---Guests---
    @GetMapping(GUESTS_URL)
    public String guests(Model model) {
        final List<GuestDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);

        return GUESTS_VIEW;
    }

//  ---Bookings---
    @GetMapping(BOOKINGS_URL)
    public String bookings(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return BOOKINGS_VIEW;
    }

//  ---Stays---
    @GetMapping(STAYS_URL)
    public String stays(Model model) {

        return STAYS_VIEW;
    }

//  ---Invoices---
    @GetMapping(INVOICES_URL)
    public String invoices(Model model) {

        return INVOICES_VIEW;
    }

//  ---CreateGuest---
    @GetMapping(CREATE_GUEST_URL)
    public String createGuestGet(Model model) {
        GuestForm guestForm = new GuestForm();

        model.addAttribute("guest", guestForm);

        return CREATE_GUEST_VIEW;
    }

    @PostMapping(CREATE_GUEST_URL)
    public String createGuestPost(@ModelAttribute("guest") @Valid GuestForm guestForm, BindingResult bindingResult, Model model) {
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

//  ---CreateBooking---
    @GetMapping(CREATE_BOOKING_GUEST_URL)
    public String createBookingGuest(Model model) {
        final List<GuestDTO> guests = guestListingService.allGuests();
        BookingForm bookingForm = new BookingForm();

        model.addAttribute("guests", guests);
        model.addAttribute("booking", bookingForm);

        return CREATE_BOOKING_GUEST_VIEW;
    }

    @PostMapping(CREATE_BOOKING_DATE_URL)
    public String createBookingDate(BookingForm bookingForm, Model model) {

        model.addAttribute("booking", bookingForm);

        return CREATE_BOOKING_DATE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_CATEGORY_URL)
    public String createBookingCategory(BookingForm bookingForm, Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("booking", bookingForm);
        model.addAttribute("categories", categories);

        return CREATE_BOOKING_CATEGORY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_SERVICE_URL)
    public String createBookingService(BookingForm bookingForm, Model model) {
        final List<ServiceDTO> services = serviceListingService.allServices();

        model.addAttribute("booking", bookingForm);
        model.addAttribute("services", services);

        return CREATE_BOOKING_SERVICE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_SUMMARY_URL)
    public String createBookingSummary(BookingForm bookingform, boolean create, Model model) {

        BookingSummaryDTO bookingSummaryDTO = bookingSummaryService.createSummary(
                bookingform.getGuestId(),
                bookingform.getRoomCategoryIds(),
                bookingform.getAmounts(),
                bookingform.getServiceIds(),
                bookingform.getCheckInDate(),
                bookingform.getCheckOutDate()
        );

        model.addAttribute("bookingSummary", bookingSummaryDTO);
        model.addAttribute("booking", bookingform);
        model.addAttribute("create", create);

        return CREATE_BOOKING_SUMMARY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(BookingForm form, Model model, boolean create) {

        if (create) {
            bookingCreationService.book(form.getGuestId(),
                    form.getRoomCategoryIds(),
                    form.getAmounts(),
                    form.getServiceIds(),
                    form.getCheckInDate(),
                    form.getCheckOutDate());

        }

        return createBookingSummary(form, true, model);

    }

//  ---Booking Details---
    @GetMapping(SHOW_BOOKING_URL)   //for check-in
    public String showBooking(@PathVariable String id, Model model) {

        BookingDetailsDTO bookingDetailsDTO =  bookingDetailsService.detailsByBookingId(id);
        model.addAttribute("bookingDetails", bookingDetailsDTO);

        return SHOW_BOOKING_VIEW;
    }




//  ---Check-In---
@GetMapping(CHECK_IN_URL)
    public String checkIn(@RequestParam("bookingId") String bookingId,
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





    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("message") String message, Model model){
        model.addAttribute("message", message);
        return ERROR_VIEW;
    }

    private static ModelAndView redirectError(String message){
        return new ModelAndView("redirect:" + ERROR_URL + "?message" + message);
    }
}
