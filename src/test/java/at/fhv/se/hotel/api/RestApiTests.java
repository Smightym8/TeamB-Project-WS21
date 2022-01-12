package at.fhv.se.hotel.api;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    RoomCategoryListingService roomCategoryListingService;

    @Test
    public void given_roomCategories_when_fetchingAllCategoriesThroughRest_then_returnEqualsCategories() {
        // given
        List<RoomCategoryDTO> categoryDTOsExpected = List.of(
                RoomCategoryDTO.builder()
                        .withId("1")
                        .withName("Single Room")
                        .build(),
                RoomCategoryDTO.builder()
                        .withId("2")
                        .withName("Double Room")
                        .build()
        );

        Mockito.when(roomCategoryListingService.allRoomCategories()).thenReturn(categoryDTOsExpected);

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/hotel/roomcategories").build().encode().toUri();

        RoomCategoryDTO[] categoryDTOsActual = this.restTemplate.getForObject(uri, RoomCategoryDTO[].class);

        // then
        assertEquals(categoryDTOsExpected.size(), categoryDTOsActual.length);

        for(int i = 0; i < categoryDTOsExpected.size(); i++) {
            assertEquals(categoryDTOsExpected.get(i).id(), categoryDTOsActual[i].id());
            assertEquals(categoryDTOsExpected.get(i).name(), categoryDTOsActual[i].name());
        }
    }
}
