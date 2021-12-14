package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;

public interface CheckOutService {
    InvoiceDTO createInvoice(String stayId) throws StayNotFoundException;

    boolean checkOut(String stayId);
}
