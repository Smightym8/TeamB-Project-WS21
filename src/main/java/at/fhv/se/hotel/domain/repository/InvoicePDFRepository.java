package at.fhv.se.hotel.domain.repository;

import org.springframework.core.io.ByteArrayResource;

import java.util.Optional;

public interface InvoicePDFRepository {
    Optional<ByteArrayResource> findInvoiceByNo(String invoiceNo);
}
