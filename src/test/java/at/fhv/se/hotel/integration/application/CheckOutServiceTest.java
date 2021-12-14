package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckOutService;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CheckOutServiceTest {

    @Autowired
    CheckOutService checkOutService;

    @MockBean
    StayRepository stayRepository;

    @MockBean
    RoomCategoryPriceService roomCategoryPriceRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    @Test
    void given_stay_when_createinvoice_then_returnexpectedinvoice() throws StayNotFoundException {
        // given
        Guest guestExpected = Guest.create(new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpected.addRoomCategory(roomCategory, 1));

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        Season.SUMMER,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = List.of(
                Room.create(
                        roomNameExpected,
                        roomStatusExpected,
                        categoriesExpected.get(0)
                )
        );

        StayId idExpected = new StayId(bookingExpected.getBookingId().id());
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.1");
        BigDecimal totalNetAmountExpected = new BigDecimal("2901.52");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("290.0");
        BigDecimal totalGrossAmountExpected = new BigDecimal("3191.52");


        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(idExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), Season.SUMMER))
                .thenReturn(roomCategoryPricesExpected.get(0));

        // when
        InvoiceDTO invoiceActual = checkOutService.createInvoice(stayExpected.getStayId().id());

        // then
        assertEquals(stayExpected.getGuest().getName().firstName(), invoiceActual.guestFirstName());
        assertEquals(stayExpected.getGuest().getName().lastName(), invoiceActual.guestLastName());
        assertEquals(stayExpected.getGuest().getAddress().streetName(), invoiceActual.streetName());
        assertEquals(stayExpected.getGuest().getAddress().streetNumber(), invoiceActual.streetNumber());
        assertEquals(stayExpected.getGuest().getAddress().zipCode(), invoiceActual.zipCode());
        assertEquals(stayExpected.getGuest().getAddress().city(), invoiceActual.city());
        assertEquals(roomCategoryPricesExpected.size(), invoiceActual.roomCategoryPrices().size());
        assertEquals(stayExpected.getCheckInDate(), invoiceActual.checkInDate());
        assertEquals(stayExpected.getCheckOutDate(), invoiceActual.checkOutDate());
        assertEquals(amountOfNightsExpected, invoiceActual.amountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceActual.localTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceActual.localTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceActual.valueAddedTaxInPercent());
        assertEquals(totalNetAmountExpected, invoiceActual.totalNetAmount());
        assertEquals(valueAddedTaxInEuroExpected, invoiceActual.valueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoiceActual.totalGrossAmount());
    }

    @Test
    void given_existingstay_whencheckout_thenreturntrue() {

        // given
        Guest guestExpected = Guest.create(new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpected.addRoomCategory(roomCategory, 1));

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        Season.SUMMER,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = List.of(
                Room.create(
                        roomNameExpected,
                        roomStatusExpected,
                        categoriesExpected.get(0)
                )
        );

        StayId idExpected = new StayId(bookingExpected.getBookingId().id());
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(idExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), Season.SUMMER))
                .thenReturn(roomCategoryPricesExpected.get(0));

        // when
        boolean statusActual = checkOutService.checkOut(stayExpected.getStayId().id());

        // then
        assertTrue(statusActual);
    }


    @Test
    void given_nonexistingstay_whencheckout_thenreturnfalse() {

        // given
        String nonExistingId = "0";

        // when
        boolean statusActual = checkOutService.checkOut(nonExistingId);

        // then
        assertFalse(statusActual);

    }
}
