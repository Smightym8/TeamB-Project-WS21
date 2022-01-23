package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.stay.Stay;

import java.util.List;

/**
 * This class is an interface that defines the functionality to create an invoice.
 * The implementation is in {@link at.fhv.se.hotel.domain.services.impl.InvoiceCreationServiceImpl}
 */
public interface InvoiceCreationService {
    /**
     * See implementation
     * {@link at.fhv.se.hotel.domain.services.impl.InvoiceCreationServiceImpl#createInvoice(Stay, List, String)}
     */
    Invoice createInvoice(Stay stay, List<String> roomNames, String action) throws SeasonNotFoundException, RoomNotFoundException;
}
