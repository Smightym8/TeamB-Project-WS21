package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RoomCategoryListingServiceTests {
    @Autowired
    RoomCategoryListingService roomCategoryListingService;

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @Test
    void given_categoryinrepository_when_fetchingbyid_then_returnequalscategory() {
        // given
        RoomCategoryId idExpected = new RoomCategoryId("42");
        RoomCategory categoryExpected = RoomCategory.create(
                idExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Mockito.when(roomCategoryRepository.roomCategoryById(idExpected)).thenReturn(Optional.of(categoryExpected));

        // when
        RoomCategoryDTO categoryActual = roomCategoryListingService.findRoomCategoryById(idExpected.id()).get();

        // then
        assertEquals(categoryExpected.getRoomCategoryId().id(), categoryActual.id());
        assertEquals(categoryExpected.getRoomCategoryName().name(), categoryActual.name());
    }
}
