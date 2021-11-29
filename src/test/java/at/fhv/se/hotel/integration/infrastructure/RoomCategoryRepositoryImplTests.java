package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RoomCategoryRepositoryImplTests {
    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_roomcategory_when_addcategorytorepository_then_returnequalscategory() {
        // given
        RoomCategoryId idExpected = new RoomCategoryId("1337");
        RoomCategory categoryExpected = RoomCategory.create(
                idExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        // when
        roomCategoryRepository.add(categoryExpected);
        em.flush();
        RoomCategory categoryActual = roomCategoryRepository.roomCategoryById(idExpected).get();

        // then
        assertEquals(categoryExpected, categoryActual);
        assertEquals(idExpected, categoryExpected.getRoomCategoryId());
    }

    @Test
    void given_2roomcategories_whenaddcategoriestorepository_then_return2equalcategories() {
        // given
        RoomCategory categoryExpected1 = RoomCategory.create(
                roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory categoryExpected2 = RoomCategory.create(
                roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );


        List<RoomCategory> roomCategoriesExpected = new ArrayList<>();
        roomCategoriesExpected.add(categoryExpected1);
        roomCategoriesExpected.add(categoryExpected2);

        // when
        List<RoomCategory> roomCategoriesActual = roomCategoryRepository.findAllRoomCategories();

        // then
        assertEquals(roomCategoriesExpected.size(), roomCategoriesActual.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(roomCategoriesExpected.get(i).getRoomCategoryName(), roomCategoriesActual.get(i).getRoomCategoryName());
        }
    }
}
