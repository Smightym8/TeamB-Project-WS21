package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;

import java.util.List;

public interface CheckOutService {

    void checkOut(String stayId, List<String> roomNames) throws StayNotFoundException;

    InvoiceDTO createInvoice(String stayId, List<String> roomNames) throws StayNotFoundException;

    void saveInvoice(String stayId, List<String> roomNames) throws StayNotFoundException;
}
