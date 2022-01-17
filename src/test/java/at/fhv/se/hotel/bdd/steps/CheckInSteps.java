package at.fhv.se.hotel.bdd.steps;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@CucumberContextConfiguration
@SpringBootTest
public class CheckInSteps {
    @Autowired
    private CheckInService checkInService;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    PlatformTransactionManager txManager;

    private TransactionStatus tx;

    @Before
    public void beforeScenario(){
        this.tx = this.txManager.getTransaction(new DefaultTransactionDefinition());
    }

    @After
    public void afterScenario(){
        this.txManager.rollback(this.tx);
    }

    @Given("a booking with the id {word}")
    public void aBookingWithTheId(String id) {
        BookingId bookingIdExpected = new BookingId(id);

        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100")))
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, 1);

        Room roomExpected = Room.create(
                new RoomName("101"),
                RoomStatus.FREE,
                roomCategoryExpected
        );

        roomRepository.add(roomExpected);
        guestRepository.add(guestExpected);
        servicesExpected.forEach(service -> serviceRepository.add(service));
        roomCategoryRepository.add(roomCategoryExpected);
        bookingRepository.add(bookingExpected);
    }

    private List<RoomDTO> roomDTOs = new ArrayList<>();
    @When("I assign the rooms for the booking with the id {word}")
    public void iAssignTheRooms(String bookingId) throws BookingNotFoundException {
        roomDTOs = checkInService.assignRooms(bookingId);
    }

    @Then("I should have rooms matching the booked categories for booking with id {word}")
    public void iShouldHaveRoomsMatchingTheBookedCategories(String bookingId) {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();

        for(int i = 0; i < roomDTOs.size(); i++) {
            assertEquals(
                    booking.getRoomCategories().get(i).getRoomCategory().getRoomCategoryName().name(),
                    roomDTOs.get(i).categoryName()
            );
        }
    }

    @Given("a booking with the id {word} and the room {word}")
    public void aBookingWithTheIdAndTheRoom(String bookingId, String roomName) {
        BookingId bookingIdExpected = new BookingId(bookingId);

        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100")))
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, 1);

        Room roomExpected = Room.create(
                new RoomName(roomName),
                RoomStatus.FREE,
                roomCategoryExpected
        );

        guestRepository.add(guestExpected);
        servicesExpected.forEach(service -> serviceRepository.add(service));
        roomCategoryRepository.add(roomCategoryExpected);
        bookingRepository.add(bookingExpected);
        roomRepository.add(roomExpected);
    }

    @When("I do the check in for the booking with id {word} and with the room {word}")
    public void iDoTheCheckIn(String bookingId, String roomName) throws BookingNotFoundException, RoomNotFoundException {

        checkInService.checkIn(bookingId, List.of(roomName));
    }

    @Then("I should have a matching stay for the booking with id {word}")
    public void iShouldHaveAMatchingStay(String bookingId) {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();
        Stay stay = stayRepository.stayById(new StayId(bookingId)).get();

        assertNotNull(stay);
        assertFalse(booking.isActive());
        assertEquals(booking.getBookingId().id(), stay.getStayId().id());

        List<Room> rooms = new ArrayList<>(stay.getRooms().keySet());
        for(int i = 0; i < booking.getRoomCategories().size(); i++) {
            assertEquals(
                    booking.getRoomCategories().get(i).getRoomCategory().getRoomCategoryName().name(),
                    rooms.get(i).getRoomCategory().getRoomCategoryName().name()
            );
        }
    }
}
