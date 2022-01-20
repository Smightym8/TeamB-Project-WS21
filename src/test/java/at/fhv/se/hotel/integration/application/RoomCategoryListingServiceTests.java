package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoomCategoryListingServiceTests {
    @Autowired
    RoomCategoryListingService roomCategoryListingService;

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @Test
    void given_categoryinrepository_when_fetchingbyid_then_returnequalscategory() throws RoomCategoryNotFoundException {
        // given
        RoomCategoryId idExpected = new RoomCategoryId("42");
        RoomCategory categoryExpected = RoomCategory.create(
                idExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Mockito.when(roomCategoryRepository.roomCategoryById(idExpected)).thenReturn(Optional.of(categoryExpected));

        // when
        RoomCategoryDTO categoryActual = roomCategoryListingService.findRoomCategoryById(idExpected.id());

        // then
        assertEquals(categoryExpected.getRoomCategoryId().id(), categoryActual.id());
        assertEquals(categoryExpected.getRoomCategoryName().name(), categoryActual.name());
        assertEquals(categoryExpected.getDescription().description(), categoryActual.description());
    }

    @Test
    void given_3categoriesinrepository_when_fetchinall_then_returnqualscategories() {
        // given
        Mockito.when(roomCategoryRepository.nextIdentity())
                .thenReturn(new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()));

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(
                        roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")
                ),
                RoomCategory.create(
                        roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room")
                ),
                RoomCategory.create(
                        roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Luxury Suite"),
                        new Description("This is a luxury suite")
                )
        );

        Mockito.when(roomCategoryRepository.findAllRoomCategories()).thenReturn(categoriesExpected);

        // when
        List<RoomCategoryDTO> categoriesActual = roomCategoryListingService.allRoomCategories();

        // then
        assertEquals(categoriesExpected.size(), categoriesActual.size());

        for(int i = 0; i < categoriesExpected.size(); i++) {
            assertEquals(categoriesExpected.get(i).getRoomCategoryId().id(), categoriesActual.get(i).id());
            assertEquals(categoriesExpected.get(i).getRoomCategoryName().name(), categoriesActual.get(i).name());
            assertEquals(categoriesExpected.get(i).getDescription().description(), categoriesActual.get(i).description());
        }
    }

    @Test
    public void given_missingRoomCategory_when_findById_then_RoomCategoryNotFoundExceptionIsThrown() {
        // given
        RoomCategoryId categoryIdExpected = new RoomCategoryId("1");

        Mockito.when(roomCategoryRepository.roomCategoryById(categoryIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomCategoryNotFoundException.class, () -> {
            roomCategoryListingService.findRoomCategoryById(categoryIdExpected.id());
        });

        String expectedMessage = "Room Category with id " + categoryIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
