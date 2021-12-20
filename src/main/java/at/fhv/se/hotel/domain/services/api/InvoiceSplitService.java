package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.stay.Stay;

import java.util.List;

public interface InvoiceSplitService {
    Invoice splitInvoice(Stay stay, List<String> roomNames);
}
