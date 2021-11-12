package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String START_BOOKING_URL = "/booking";
    private static final String ALL_BOOKINGS_URL = "/bookinglist";
    private static final String BOOKING_SUMMARY_URL = "/bookingSummary";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";
    private static final String CHOOSE_GUEST_URL = "/chooseguest";
    private static final String CHOOSE_SERVICE_URL = "/chooseservice";
    private static final String CHOOSE_DATES_URL = "/choosedates";
    private static final String CREATE_BOOKING_URL = "/createbooking";
    private static final String ERROR_URL = "/displayerror";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String ALL_BOOKINGS_VIEW = "allBookings";
    private static final String BOOKING_SUMMARY_VIEW = "bookingSummary";
    private static final String CREATE_BOOKING_VIEW = "startCreateBooking";
    private static final String CHOOSE_CATEGORY_VIEW = "chooseCategory";
    private static final String CHOOSE_GUEST_VIEW = "chooseGuest";
    private static final String CHOOSE_SERVICE_VIEW = "chooseService";
    private static final String CHOOSE_DATES_VIEW = "chooseBookingDates";
    private static final String ERROR_VIEW = "errorView";

    // Services
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

    /**
     * This method handles a get request on /.
     * @return MAIN_MENU_VIEW - contains the main menu view.
     */
    @GetMapping(MAIN_MENU_URL)
    public String mainMenu() {
        return MAIN_MENU_VIEW;
    }

    /**
     * This method handles a get request on /booking and passes the bookings to the allBookings view.
     * @param model contains the model to be presented in the view.
     * @return ALL_BOOKINGS_VIEW contains the view with all bookings.
     */
    @GetMapping(ALL_BOOKINGS_URL)
    public String allBookings(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return ALL_BOOKINGS_VIEW;
    }

    @GetMapping(START_BOOKING_URL)
    public String createBookingForm(Model model) {
        BookingForm bookingForm = new BookingForm();

        model.addAttribute("form", bookingForm);

        return CREATE_BOOKING_VIEW;
    }

    @PostMapping(CHOOSE_CATEGORY_URL)
    public String chooseRoomCategories(@ModelAttribute("form") BookingForm form, Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("form", form);

        return CHOOSE_CATEGORY_VIEW;
    }

    @PostMapping(CHOOSE_GUEST_URL)
    public String chooseGuestForBooking(@ModelAttribute("form") BookingForm form, Model model) {

        List<GuestDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);
        model.addAttribute("form", form);

        return CHOOSE_GUEST_VIEW;
    }

    @PostMapping(CHOOSE_SERVICE_URL)
    public String chooseServices(@ModelAttribute("form") BookingForm form, Model model) {
        final List<ServiceDTO> services = serviceListingService.allServices();

        model.addAttribute("services", services);
        model.addAttribute("form", form);

        return CHOOSE_SERVICE_VIEW;
    }

    @PostMapping(CHOOSE_DATES_URL)
    public String chooseCheckInCheckOutDate(@ModelAttribute("form") BookingForm form, Model model) {

        model.addAttribute("form", form);

        return CHOOSE_DATES_VIEW;
    }

    @PostMapping(BOOKING_SUMMARY_URL)
    public String showSummary(@ModelAttribute("form") BookingForm form,
                              @RequestParam("isCreated") boolean isCreated,
                              Model model) {

        BookingSummaryDTO bookingSummaryDTO = bookingSummaryService.createSummary(
                form.getGuestId(),
                form.getRoomCategoryIds(),
                form.getServiceIds(),
                form.getCheckInDate(),
                form.getCheckOutDate()
        );

        model.addAttribute("bookingSummary", bookingSummaryDTO);
        model.addAttribute("form", form);
        model.addAttribute("isCreated", isCreated);

        return BOOKING_SUMMARY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(@ModelAttribute("form") BookingForm form,
                                Model model){
        bookingCreationService.book(form.getGuestId(),
                form.getRoomCategoryIds(),
                form.getServiceIds(),
                form.getCheckInDate(),
                form.getCheckOutDate());
        return showSummary(form, true, model);
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
