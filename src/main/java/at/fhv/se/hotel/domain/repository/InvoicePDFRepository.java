package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import org.springframework.core.io.ByteArrayResource;

import java.util.Optional;

public interface InvoicePDFRepository {
    Optional<ByteArrayResource> findInvoiceByNo(String invoiceNo);

    void saveAsPDF(InvoiceDTO invoiceDTO);
}
