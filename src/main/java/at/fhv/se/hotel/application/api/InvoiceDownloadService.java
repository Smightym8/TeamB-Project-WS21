package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import org.springframework.core.io.ByteArrayResource;

public interface InvoiceDownloadService {
    ByteArrayResource download(String invoiceNo) throws InvoiceNotFoundException;
}
