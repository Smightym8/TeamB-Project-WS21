package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class InvoiceRepositoryImplTests {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    StayRepository stayRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_invoice_when_invoicerepository_then_returnequalinvoice() {
        //given
        List<Service> servicesExpected = Arrays.asList(
            Service.create(serviceRepository.nextIdentity(),
                    new ServiceName("TV"),
                    new Price(new BigDecimal("100"))),
                        Service.create(serviceRepository.nextIdentity(),
                    new ServiceName("Breakfast"),
                    new Price(new BigDecimal("100")))
        );
        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        BookingId idExpected = new BookingId("1337");
        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 12, 5),
                LocalDate.of(2021, 12, 15),
                idExpected,
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );
        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );
        bookingExpected.addRoomCategory(categoriesExpected.get(0), 1);
        bookingExpected.addRoomCategory(categoriesExpected.get(1), 1);

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> categoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                ),
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(1),
                        new BigDecimal("500")
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomNameExpected),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        InvoiceId invoiceIdExpected = new InvoiceId("1337");
        String invoiceNumberExpected = "30112021001";
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);
        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("100");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("200");
        BigDecimal discountInPercentExpected = new BigDecimal("0");
        BigDecimal discountInEuroExpected = new BigDecimal("0");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("200");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("201.52");
        BigDecimal totalGrossAmountExpected = new BigDecimal("301.52");

        Invoice invoiceExpected = Invoice.create(
                invoiceIdExpected,
                invoiceNumberExpected,
                stayExpected,
                categoryPricesExpected,
                servicesExpected,
                roomsForInvoice,
                amountOfNightsExpected,
                localTaxPerPersonExpected,
                localTaxTotalExpected,
                valueAddedTaxInPercentExpected,
                valueAddedTaxInEuroExpected,
                totalNetAmountBeforeDiscountExpected,
                discountInPercentExpected,
                discountInEuroExpected,
                totalNetAmountAfterDiscountExpected,
                totalNetAmountAfterLocalTaxExpected,
                totalGrossAmountExpected
        );

        //when
        seasonRepository.add(winterSeason);
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        this.guestRepository.add(guestExpected);
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        this.bookingRepository.add(bookingExpected);
        categoryPricesExpected.forEach(roomCategoryPrice -> this.roomCategoryPriceRepository.add(roomCategoryPrice));
        this.roomRepository.add(roomsExpected.entrySet().iterator().next().getKey());
        this.invoiceRepository.add(invoiceExpected);
        em.flush();

        Invoice invoiceActual = this.invoiceRepository.invoiceById(invoiceIdExpected).get();

        //then
        assertEquals(invoiceExpected, invoiceActual);
        assertEquals(invoiceIdExpected, invoiceActual.getInvoiceId());
        assertEquals(stayExpected, invoiceActual.getStay());
        assertEquals(categoryPricesExpected.size(), invoiceActual.getRoomCategoryPriceList().size());
        assertEquals(servicesExpected.size(), invoiceActual.getServices().size());
        assertEquals(amountOfNightsExpected, invoiceActual.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceActual.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceActual.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceActual.getValueAddedTaxInPercent());
        assertEquals(valueAddedTaxInEuroExpected, invoiceActual.getValueAddedTaxInEuro());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoiceActual.getTotalNetAmountBeforeDiscount());
        assertEquals(discountInPercentExpected, invoiceActual.getDiscountInPercent());
        assertEquals(discountInEuroExpected, invoiceActual.getDiscountInEuro());
        assertEquals(totalNetAmountAfterDiscountExpected, invoiceActual.getTotalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoiceActual.getTotalNetAmountAfterLocalTax());
        assertEquals(totalGrossAmountExpected, invoiceActual.getTotalGrossAmount());
    }

    @Test
    void given_3invoices_when_fetchallinvoices_then_returnequalinvoices() {
        // given
        List<Guest> guestsExpected = List.of(
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Michael", "Spiegel"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 3, 20),
                        "+43 660 123 456 789",
                        "michael.spiegel@students.fhv.at",
                        0,
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Ali", "Cinar"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1997, 8, 27),
                        "+43 676 123 456 789",
                        "ali.cinar@students.fhv.at",
                        0,
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Umut", "Caglayan"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 7, 7),
                        "+43 676 123 456 789",
                        "umut.caglayan@students.fhv.at",
                        0,
                        Collections.emptyList()
                )
        );

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );
        List<Booking> bookingsExpected = List.of(
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(0),
                        servicesExpected,  2, 1,
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(1),
                        servicesExpected, 2, 1,
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(2),
                        servicesExpected, 2, 1,
                        "Nothing")
        );
        bookingsExpected.get(0).addRoomCategory(categoriesExpected.get(0), 1);
        bookingsExpected.get(1).addRoomCategory(categoriesExpected.get(1), 1);
        bookingsExpected.get(2).addRoomCategory(categoriesExpected.get(0),2);

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> categoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                ),
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(1),
                        new BigDecimal("500")
                )
        );

        List<String> roomsNameExpected = Arrays.asList("Room1","Room2","Room3");
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomsNameExpected.get(0)),
                        roomStatusExpected,
                        categoriesExpected.get(0)), false,
                Room.create(
                        new RoomName(roomsNameExpected.get(1)),
                        roomStatusExpected,
                        categoriesExpected.get(1)), false,
                Room.create(
                        new RoomName(roomsNameExpected.get(2)),
                        roomStatusExpected,
                        categoriesExpected.get(1)), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0), roomsExpected),
                Stay.create(bookingsExpected.get(1), roomsExpected),
                Stay.create(bookingsExpected.get(2), roomsExpected)
        );

        List<String> invoiceNumbersExpected = List.of(
                "30112021001",
                "30112021002",
                "30112021003"
        );

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("100");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("200");
        BigDecimal discountInPercentExpected = new BigDecimal("0");
        BigDecimal discountInEuroExpected = new BigDecimal("0");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("200");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("201.52");
        BigDecimal totalGrossAmountExpected = new BigDecimal("301.52");

        List<Invoice> invoicesExpected = invoiceNumbersExpected.stream()
                .map(invoiceNo -> Invoice.create(
                        invoiceRepository.nextIdentity(),
                        invoiceNo,
                        staysExpected.listIterator().next(),
                        categoryPricesExpected,
                        servicesExpected,
                        roomsForInvoice,
                        amountOfNightsExpected,
                        localTaxPerPersonExpected,
                        localTaxTotalExpected,
                        valueAddedTaxInPercentExpected,
                        valueAddedTaxInEuroExpected,
                        totalNetAmountBeforeDiscountExpected,
                        discountInPercentExpected,
                        discountInEuroExpected,
                        totalNetAmountAfterDiscountExpected,
                        totalNetAmountAfterLocalTaxExpected,
                        totalGrossAmountExpected
                )).collect(Collectors.toList());
        //when
        seasonRepository.add(winterSeason);
        guestsExpected.forEach(guest -> this.guestRepository.add(guest));
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        bookingsExpected.forEach(booking -> this.bookingRepository.add(booking));
        categoryPricesExpected.forEach(roomCategoryPrice -> this.roomCategoryPriceRepository.add(roomCategoryPrice));

        for (Map.Entry<Room, Boolean> entry : roomsExpected.entrySet()) {
            this.roomRepository.add(entry.getKey());
        }

        staysExpected.forEach(stay -> this.stayRepository.add(stay));
        invoicesExpected.forEach(invoice -> this.invoiceRepository.add(invoice));
        em.flush();
        List<Invoice> invoicesActual = this.invoiceRepository.findAllInvoices();

        //then
        assertEquals(invoicesExpected.size(), invoicesActual.size());
        for (Invoice i : invoicesActual) {
            assertTrue(invoicesExpected.contains(i));
        }
    }

    @Test
    public void given_3invoicesWithCreationDate_when_invoicesByDate_then_return3Invoices() {
        // given
        List<Guest> guestsExpected = List.of(
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Michael", "Spiegel"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 3, 20),
                        "+43 660 123 456 789",
                        "michael.spiegel@students.fhv.at",
                        0,
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Ali", "Cinar"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1997, 8, 27),
                        "+43 676 123 456 789",
                        "ali.cinar@students.fhv.at",
                        0,
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Umut", "Caglayan"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 7, 7),
                        "+43 676 123 456 789",
                        "umut.caglayan@students.fhv.at",
                        0,
                        Collections.emptyList()
                )
        );

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );
        List<Booking> bookingsExpected = List.of(
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(0),
                        servicesExpected,  2, 1,
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(1),
                        servicesExpected, 2, 1,
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(2),
                        servicesExpected, 2, 1,
                        "Nothing")
        );
        bookingsExpected.get(0).addRoomCategory(categoriesExpected.get(0), 1);
        bookingsExpected.get(1).addRoomCategory(categoriesExpected.get(1), 1);
        bookingsExpected.get(2).addRoomCategory(categoriesExpected.get(0),2);

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> categoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                ),
                RoomCategoryPrice.create(
                        roomCategoryPriceRepository.nextIdentity(),
                        winterSeason,
                        categoriesExpected.get(1),
                        new BigDecimal("500")
                )
        );

        List<String> roomsNameExpected = Arrays.asList("Room1","Room2","Room3");
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomsNameExpected.get(0)),
                        roomStatusExpected,
                        categoriesExpected.get(0)), false,
                Room.create(
                        new RoomName(roomsNameExpected.get(1)),
                        roomStatusExpected,
                        categoriesExpected.get(1)), false,
                Room.create(
                        new RoomName(roomsNameExpected.get(2)),
                        roomStatusExpected,
                        categoriesExpected.get(1)), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0), roomsExpected),
                Stay.create(bookingsExpected.get(1), roomsExpected),
                Stay.create(bookingsExpected.get(2), roomsExpected)
        );

        List<String> invoiceNumbersExpected = List.of(
                "30112021001",
                "30112021002",
                "30112021003"
        );

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("100");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("200");
        BigDecimal discountInPercentExpected = new BigDecimal("0");
        BigDecimal discountInEuroExpected = new BigDecimal("0");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("200");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("201.52");
        BigDecimal totalGrossAmountExpected = new BigDecimal("301.52");

        // Invoice date is set in create method to current date
        LocalDate invoiceDateExpected = LocalDate.now();
        List<Invoice> invoicesExpected = invoiceNumbersExpected.stream()
                .map(invoiceNo -> Invoice.create(
                        invoiceRepository.nextIdentity(),
                        invoiceNo,
                        staysExpected.listIterator().next(),
                        categoryPricesExpected,
                        servicesExpected,
                        roomsForInvoice,
                        amountOfNightsExpected,
                        localTaxPerPersonExpected,
                        localTaxTotalExpected,
                        valueAddedTaxInPercentExpected,
                        valueAddedTaxInEuroExpected,
                        totalNetAmountBeforeDiscountExpected,
                        discountInPercentExpected,
                        discountInEuroExpected,
                        totalNetAmountAfterDiscountExpected,
                        totalNetAmountAfterLocalTaxExpected,
                        totalGrossAmountExpected
                )).collect(Collectors.toList());
        //when
        seasonRepository.add(winterSeason);
        guestsExpected.forEach(guest -> this.guestRepository.add(guest));
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        bookingsExpected.forEach(booking -> this.bookingRepository.add(booking));
        categoryPricesExpected.forEach(roomCategoryPrice -> this.roomCategoryPriceRepository.add(roomCategoryPrice));

        for (Map.Entry<Room, Boolean> entry : roomsExpected.entrySet()) {
            this.roomRepository.add(entry.getKey());
        }

        staysExpected.forEach(stay -> this.stayRepository.add(stay));
        invoicesExpected.forEach(invoice -> this.invoiceRepository.add(invoice));
        em.flush();

        List<Invoice> invoicesActual = this.invoiceRepository.invoicesByDate(invoiceDateExpected);

        //then
        assertEquals(invoicesExpected.size(), invoicesActual.size());
        for(Invoice i : invoicesExpected) {
            assertTrue(invoicesActual.contains(i));
        }
    }
}
