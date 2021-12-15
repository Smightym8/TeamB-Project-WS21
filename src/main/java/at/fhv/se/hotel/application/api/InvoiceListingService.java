package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.InvoiceListingDTO;

import java.util.List;

public interface InvoiceListingService {
    List<InvoiceListingDTO> allInvoices();
}
