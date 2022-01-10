package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;

import java.util.List;

public interface CheckOutService {

    void checkOut(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;

    InvoiceDTO createInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;

    void saveInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;
}
