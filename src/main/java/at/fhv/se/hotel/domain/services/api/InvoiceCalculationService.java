package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.stay.Stay;

public interface InvoiceCalculationService {
    Invoice calculateInvoice(Stay stay);
}
