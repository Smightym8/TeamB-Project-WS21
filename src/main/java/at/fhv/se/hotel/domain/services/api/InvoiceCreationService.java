package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.stay.Stay;

import java.util.List;

public interface InvoiceCreationService {
    Invoice createInvoice(Stay stay, List<String> roomNames, String action) throws SeasonNotFoundException, RoomNotFoundException;
}
