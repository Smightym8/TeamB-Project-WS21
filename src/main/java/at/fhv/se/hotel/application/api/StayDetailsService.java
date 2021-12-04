package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.StayDetailsDTO;

public interface StayDetailsService {
    StayDetailsDTO detailsById(String stayId);
}
