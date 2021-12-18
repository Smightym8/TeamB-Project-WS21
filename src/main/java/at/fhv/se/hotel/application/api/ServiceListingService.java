package at.fhv.se.hotel.application.api;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import at.fhv.se.hotel.application.impl.ServiceListingServiceImpl;
import java.util.List;
import java.util.Optional;

/**
 * This class represents an interface that defines the functionality to get all services
 * The implementation is in {@link ServiceListingServiceImpl}
 */
public interface ServiceListingService {
    /**
     * See implementation {@link ServiceListingServiceImpl#allServices()}
     */
    List<ServiceDTO> allServices();
    Optional<ServiceDTO> findServiceById(String id) throws ServiceNotFoundException;
}
