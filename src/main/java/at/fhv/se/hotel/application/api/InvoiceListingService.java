package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.impl.InvoiceListingServiceImpl;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all invoices or a specific one by id.
 * The implementation is in {@link InvoiceListingServiceImpl}
 */
public interface InvoiceListingService {

    /**
     * See implementation {@link InvoiceListingServiceImpl#allInvoices()}
     */
    List<InvoiceListingDTO> allInvoices();

    /**
     * See implementation {@link InvoiceListingServiceImpl#findInvoiceById(String)}
     */
    InvoiceDTO findInvoiceById(String id) throws InvoiceNotFoundException;
}
