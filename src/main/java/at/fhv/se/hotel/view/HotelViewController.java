package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.api.exception.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.view.forms.*;
import org.apache.fop.apps.FOPException;
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
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HotelViewController {
// TODO: change in html value to th:value, href to to:href

    /* ----- SPA ----- */
    private static final String SPA_URL = "/spa";
    // Starts searching in templates
    private static final String SPA_VIEW = "index";

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

    /*----- Modify Room -----*/
    private static final String ROOM_URL = "/room/{name}";
    private static final String MODIFY_ROOM_URL = "/modifyRoom";
    private static final String MODIFY_ROOM_VIEW = "modifyRoom";

    /*----- Create Guest -----*/
    private static final String CREATE_GUEST_URL = "/createguest";
    private static final String CREATE_GUEST_VIEW = "createGuest";

    /*----- Modify Guest -----*/
    private static final String GUEST_URL = "/guest/{id}";
    private static final String MODIFY_GUEST_URL = "/modifyGuest";
    private static final String MODIFY_GUEST_VIEW = "modifyGuest";

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

    private static final String SAVE_INVOICE_URL = "/saveinvoice/{id}";

    /*----- Invoice Download -----*/
    private static final String INVOICE_DOWNLOAD_URL = "/download-invoice/{invoiceNo}";

    /*----- Invoice View -----*/
    private static final String INVOICE_DETAILS_URL = "/invoice-details/{id}";
    private static final String INVOICE_DETAILS_VIEW = "invoiceDetails";

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

    @Autowired
    RoomListingService roomListingService;

    @Autowired
    RoomModifyService roomModifyService;

    @Autowired
    GuestModifyService guestModifyService;

    @Autowired
    SeasonListingService seasonListingService;


    /*--------------------------------------------------------------------------------------------------------------------*/

    /*----- SPA -----*/
    @GetMapping(SPA_URL)
    public ModelAndView spa() {
        return new ModelAndView(SPA_VIEW);
    }

    /*----- Home -----*/
    @GetMapping(HOME_URL)
    public ModelAndView home(Model model) {
        final List<BookingListingDTO> bookings = bookingListingService.allBookings();
        final List<StayListingDTO> stays = stayListingService.allStays();

        model.addAttribute("bookings", bookings);
        model.addAttribute("stays", stays);

        return new ModelAndView(HOME_VIEW);
    }

    /*----- Rooms -----*/
    @GetMapping(ROOMS_URL)
    public ModelAndView rooms(Model model) {
        List<RoomDTO> rooms = roomListingService.allRooms();

        model.addAttribute("rooms", rooms);

        return new ModelAndView(ROOMS_VIEW);
    }

    @GetMapping(ROOM_URL)
    public ModelAndView room(@PathVariable String name, Model model) {
        RoomDTO roomDTO;
        try {
            roomDTO = roomListingService.roomByName(name);

            // Don't allow to reach room view with an occupied room
            if(roomDTO.roomStatus().equals("OCCUPIED")) {
                return redirectError("A room with status occupied can't be changed.");
            }

            RoomForm roomForm = new RoomForm(roomDTO.name(), roomDTO.categoryName(), roomDTO.roomStatus());
            List<String> roomStates = new ArrayList<>();
            Arrays.stream(RoomStatus.values()).forEach(status -> roomStates.add(status.name()));

            model.addAttribute("roomForm", roomForm);
            model.addAttribute("states", roomStates);
        } catch (RoomNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView(MODIFY_ROOM_VIEW);
    }

    @PostMapping(MODIFY_ROOM_URL)
    public ModelAndView modifyRoom(@ModelAttribute("roomForm") RoomForm roomForm) {
        try {
            roomModifyService.modifyStatus(roomForm.getRoomName(), roomForm.getRoomStatus());
        } catch (RoomNotFoundException | InvalidParameterException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView("redirect:" + ROOMS_URL);
    }

    /*----- Pricing -----*/
    // TODO: Change View test
    @GetMapping(PRICING_URL)
    public ModelAndView pricing(Model model) {
        List<SeasonWithPricesDTO> seasonsWithPrices = seasonListingService.allSeasonsWithPrices();
        List<RoomCategoryDTO> roomCategories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("seasonsWithPrices", seasonsWithPrices);
        model.addAttribute("roomCategories", roomCategories);

        return new ModelAndView(PRICING_VIEW);
    }

    /*----- Guests -----*/
    @GetMapping(GUESTS_URL)
    public ModelAndView guests(Model model) {
        final List<GuestListingDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);

        return new ModelAndView(GUESTS_VIEW);
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
                guestForm.getCountry(),
                guestForm.getDiscountInPercent()
        );

        return "redirect:" + GUESTS_URL;
    }

    /*----- Modify Guest -----*/
    @GetMapping(GUEST_URL)
    public ModelAndView guest(@PathVariable("id") String id, Model model) {
        // Get GuestDetailsDTO and fill GuestForm with it
        GuestDetailsDTO guest;
        try {
            guest = guestListingService.findGuestById(id);

            GuestForm guestForm = new GuestForm(
                    guest.id(),
                    guest.firstName(),
                    guest.lastName(),
                    guest.gender(),
                    guest.mailAddress(),
                    guest.phoneNumber(),
                    guest.birthDate(),
                    guest.streetName(),
                    guest.streetNumber(),
                    guest.zipCode(),
                    guest.city(),
                    guest.country(),
                    guest.discountInPercent()
            );

            model.addAttribute("guest", guestForm);
        } catch (GuestNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView(MODIFY_GUEST_VIEW);
    }

    @PostMapping(MODIFY_GUEST_URL)
    public ModelAndView modifyGuest(@Valid @ModelAttribute("guest") GuestForm guestForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(MODIFY_GUEST_VIEW);
        }

        try {
            guestModifyService.modifyGuest(
                    guestForm.getGuestId(),
                    guestForm.getFirstName(),
                    guestForm.getLastName(),
                    guestForm.getGender(),
                    guestForm.getStreetName(),
                    guestForm.getStreetNumber(),
                    guestForm.getCity(),
                    guestForm.getZipCode(),
                    guestForm.getCountry(),
                    guestForm.getBirthDate(),
                    guestForm.getPhoneNumber(),
                    guestForm.geteMail(),
                    guestForm.getDiscountInPercent()
            );
        } catch (GuestNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView("redirect:" + GUESTS_URL);
    }

    /*----- Create Booking -----*/
    @GetMapping(CREATE_BOOKING_DATE_URL)
    public String createBookingDate(Model model) {
        BookingForm bookingForm = new BookingForm();
        GuestForm guestForm = new GuestForm();

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("guestForm", guestForm);

        return CREATE_BOOKING_DATE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_CATEGORY_URL)
    public String createBookingCategory(@ModelAttribute("bookingForm") BookingForm bookingForm,
                                        @ModelAttribute("guestForm") GuestForm guestForm,
                                        Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("guestForm", guestForm);
        model.addAttribute("categories", categories);

        return CREATE_BOOKING_CATEGORY_VIEW;
    }

    @PostMapping(CREATE_BOOKING_SERVICE_URL)
    public String createBookingService(@ModelAttribute("bookingForm") BookingForm bookingForm,
                                       @ModelAttribute("guestForm") GuestForm guestForm,
                                       @RequestParam(value="action") String action,
                                       Model model) {
        if (action.equals("back")){
            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);

            return CREATE_BOOKING_DATE_VIEW;
        }else {
            final List<ServiceDTO> services = serviceListingService.allServices();

            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);
            model.addAttribute("services", services);

            return CREATE_BOOKING_SERVICE_VIEW;
        }

    }

    @PostMapping(CREATE_BOOKING_GUEST_URL)
    public ModelAndView createBookingGuest(@ModelAttribute("bookingForm") BookingForm bookingForm,
                                           @ModelAttribute("guestForm") GuestForm guestForm,
                                           @RequestParam(value="action") String action,
                                           Model model) {
        if(action.equals("back")){
            final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);
            model.addAttribute("categories", categories);

            return new ModelAndView(CREATE_BOOKING_CATEGORY_VIEW);
        } else {
            List<GuestListingDTO> guests = guestListingService.allGuests();

            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);
            model.addAttribute("guests", guests);

            return new ModelAndView(CREATE_BOOKING_GUEST_VIEW);
        }
    }

    @PostMapping(CREATE_BOOKING_SUMMARY_URL)
    public ModelAndView createBookingSummary(
            @ModelAttribute("bookingForm") BookingForm bookingForm,
            @Valid @ModelAttribute("guestForm") GuestForm guestForm,
            BindingResult guestFormResult,
            @RequestParam("isCreated") boolean isCreated,
            @RequestParam(value="action") String action,
            Model model) {

        if (action.equals("back")){
            final List<ServiceDTO> services = serviceListingService.allServices();

            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);
            model.addAttribute("services", services);

            return new ModelAndView(CREATE_BOOKING_SERVICE_VIEW);
        }
        if((bookingForm.getGuestId() == null) && (guestFormResult.hasErrors())) {
            return new ModelAndView(CREATE_BOOKING_GUEST_VIEW);
        }

        BookingDetailsDTO bookingDetailsDTO;
        try {
        bookingDetailsDTO = bookingSummaryService.createSummary(
                bookingForm.getGuestId(),
                guestForm.getFirstName(),
                guestForm.getLastName(),
                guestForm.getStreetName(),
                guestForm.getStreetNumber(),
                guestForm.getZipCode(),
                guestForm.getCity(),
                guestForm.getCountry(),
                bookingForm.getRoomCategoryIds(),
                bookingForm.getAmountsOfRoomCategories(),
                bookingForm.getServiceIds(),
                bookingForm.getCheckInDate(),
                bookingForm.getCheckOutDate(),
                bookingForm.getAmountOfAdults(),
                bookingForm.getAmountOfChildren(),
                bookingForm.getAdditionalInformation()
        );
        } catch (ServiceNotFoundException | RoomCategoryNotFoundException | GuestNotFoundException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("bookingSummary", bookingDetailsDTO);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("isCreated", isCreated);

        return new ModelAndView(CREATE_BOOKING_SUMMARY_VIEW);
    }

    @PostMapping(CREATE_BOOKING_URL)
    // TODO: TEST
    public ModelAndView createBooking(
            @ModelAttribute("bookingForm") BookingForm bookingForm,
            @ModelAttribute("guestForm") GuestForm guestForm,
            @RequestParam(value="action") String action,
            Model model) {

        if (action.equals("back")){
            List<GuestListingDTO> guests = guestListingService.allGuests();

            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("guestForm", guestForm);
            model.addAttribute("guests", guests);

            return new ModelAndView(CREATE_BOOKING_GUEST_VIEW);
        } else {
            String bookingId;
            try {
                String guestId = bookingForm.getGuestId() == null || bookingForm.getGuestId().isEmpty()
                        ?   guestCreationService.createGuest(
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
                                guestForm.getCountry(),
                                guestForm.getDiscountInPercent()
                            )
                        :   bookingForm.getGuestId();

                bookingId = bookingCreationService.book(
                        guestId,
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
        } catch (BookingNotFoundException e) {
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
        } catch (BookingNotFoundException e) {
            return redirectError(e.getMessage());
        }
        model.addAttribute("bookingDetails", bookingDetailsDTO);

        return new ModelAndView(BOOKING_DETAILS_VIEW);
    }

    /*----- Check-In -----*/
    @GetMapping(CHECK_IN_URL)
    public ModelAndView showAssignedRooms(
            @RequestParam("bookingId") String bookingId,
            Model model) {

        List<RoomDTO> freeRooms = roomListingService.allFreeRooms();
        List<RoomDTO> assignedRooms;
        CheckInForm checkInForm;
        try {
            List<String> roomNames = new ArrayList<>();
            assignedRooms = checkInService.assignRooms(bookingId);
            assignedRooms.forEach(room -> roomNames.add(room.name()));

            checkInForm = new CheckInForm(bookingId, roomNames);

        } catch (BookingNotFoundException | NotEnoughRoomsException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("bookingId", bookingId);
        model.addAttribute("checkInForm", checkInForm);
        model.addAttribute("assignedRooms", assignedRooms);
        model.addAttribute("freeRooms", freeRooms);

        return new ModelAndView(CHECK_IN_VIEW);
    }

    @PostMapping(CHECK_IN_URL)
    public ModelAndView checkIn(@Valid @ModelAttribute("checkInForm") CheckInForm checkInForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ModelAndView(CHECK_IN_VIEW);
        }

        try {
            checkInService.checkIn(
                    checkInForm.getBookingId(),
                    checkInForm.getRoomNames()
            );
        } catch (BookingNotFoundException | RoomNotFoundException | RoomAlreadyOccupiedException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView("redirect:" + STAYS_URL);
    }


    /*----- Check-Out -----*/
    @GetMapping(STAY_DETAILS_URL)
    public ModelAndView showStay(@PathVariable String id, Model model) {
        StayDetailsDTO stayDetailsDTO;
        InvoiceForm invoiceForm = new InvoiceForm();

        try {
            stayDetailsDTO = stayDetailsService.detailsById(id);
            model.addAttribute("stayDetails", stayDetailsDTO);
            model.addAttribute("invoiceForm", invoiceForm);
        } catch (StayNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView(STAY_DETAILS_VIEW);
    }


    @GetMapping(INVOICE_URL)
    public ModelAndView showInvoice(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm,
                                    @RequestParam(value="action") String action,
                                    @PathVariable String id,
                                    Model model) {

        InvoiceDTO invoiceDTO;
        StayDetailsDTO stayDetailsDTO;

        try {
            invoiceDTO = checkOutService.createInvoice(id, invoiceForm.getRoomNames(), action);
            stayDetailsDTO = stayDetailsService.detailsById(id);
        } catch (StayNotFoundException | RoomNotFoundException | SeasonNotFoundException e) {
            return redirectError(e.getMessage());
        }

        model.addAttribute("invoice", invoiceDTO);
        model.addAttribute("invoiceForm", invoiceForm);
        model.addAttribute("stayDetails", stayDetailsDTO);
        model.addAttribute("action", action);

        return new ModelAndView(INVOICE_VIEW);

    }

    @GetMapping(SAVE_INVOICE_URL)
    public ModelAndView createInvoiceOrCheckout(@ModelAttribute("invoiceForm") InvoiceForm invoiceForm,
                                      @RequestParam(value="action") String action,
                                      @PathVariable String id){

        if (action.equals("createInvoice")) {
            try {
                checkOutService.saveInvoice(id, invoiceForm.getRoomNames(), action);
            } catch (StayNotFoundException | RoomNotFoundException | SeasonNotFoundException e) {
                return redirectError(e.getMessage());
            }

            return new ModelAndView("redirect:" + STAY_DETAILS_URL);
        } else if(action.equals("checkOut")) {
            try {
                checkOutService.checkOut(id, invoiceForm.getRoomNames(), action);
            } catch (StayNotFoundException | RoomNotFoundException | SeasonNotFoundException e) {
                return redirectError(e.getMessage());
            }

            return new ModelAndView("redirect:" + HOME_URL);
        }

        return redirectError("There was an error.");
    }

    /*----- Invoice Download -----*/
    @GetMapping(INVOICE_DOWNLOAD_URL)
    public ResponseEntity<ByteArrayResource> downloadInvoice(@PathVariable("invoiceNo") String invoiceNumber) {
        ByteArrayResource resource = null;

        try {
            resource = invoiceDownloadService.download(invoiceNumber);
        } catch (InvoiceNotFoundException | FOPException | JAXBException | IOException | TransformerException e) {
            // TODO: How to redirect?
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Invoice_" + invoiceNumber + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                // Content-Length
                .contentLength(resource.contentLength())
                .body(resource);
    }

    /*----- Invoice Details -----*/
    // TODO: Test
    @GetMapping(INVOICE_DETAILS_URL)
    public ModelAndView invoiceDetails(@PathVariable("id") String id, Model model) {
        // Get InvoiceDTO
        InvoiceDTO invoice;
        try {
            invoice = invoiceListingService.findInvoiceById(id);

            model.addAttribute("invoice", invoice);
        } catch (InvoiceNotFoundException e) {
            return redirectError(e.getMessage());
        }

        return new ModelAndView(INVOICE_DETAILS_VIEW);
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
