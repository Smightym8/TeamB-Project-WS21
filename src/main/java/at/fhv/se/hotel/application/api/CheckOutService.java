package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.InvoiceDTO;

public interface CheckOutService {
    InvoiceDTO createInvoice(String stayId);

    boolean checkOut(String stayId);
}
