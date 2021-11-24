package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayListingService;
import at.fhv.se.hotel.application.dto.StayDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StayListingServiceImpl implements StayListingService {
    @Override
    public List<StayDTO> allStays() {
        return null;
    }
}
