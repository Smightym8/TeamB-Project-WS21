package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;

import java.util.List;

public interface InvoiceListingService {
    List<InvoiceListingDTO> allInvoices();

    InvoiceDTO findInvoiceById(String id) throws InvoiceNotFoundException;
}
