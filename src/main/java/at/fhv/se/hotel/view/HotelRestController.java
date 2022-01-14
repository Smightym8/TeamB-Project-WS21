package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/hotel")
public class HotelRestController {
    private static final String BOOKING_ROOMCATEGORIES_URL = "/roomcategories";
    private static final String BOOKING_SERVICES_URL = "/services";

    @Autowired
    RoomCategoryListingService roomCategoryListingService;

    @Autowired
    ServiceListingService serviceListingService;

    @GetMapping(BOOKING_ROOMCATEGORIES_URL)
    @ResponseBody
    public RoomCategoryDTO[] allRoomCategories() {
        final List<RoomCategoryDTO> roomCategoryList = roomCategoryListingService.allRoomCategories();
        RoomCategoryDTO[] allRoomCategoriesArray = new RoomCategoryDTO[roomCategoryList.size()];

        return roomCategoryList.toArray(allRoomCategoriesArray);
    }

    @GetMapping(BOOKING_SERVICES_URL)
    @ResponseBody
    public ServiceDTO[] allServices() {
        final List<ServiceDTO> serviceList = serviceListingService.allServices();
        ServiceDTO[] allServicesArray = new ServiceDTO[serviceList.size()];

        return serviceList.toArray(allServicesArray);
    }
}