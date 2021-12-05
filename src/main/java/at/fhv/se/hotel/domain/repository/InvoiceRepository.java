package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    List<Invoice> findAllInvoices();

    InvoiceId nextIdentity();

    void add(Invoice invoice);

    Optional<Invoice> invoiceById(InvoiceId invoiceId);

    List<Invoice> invoicesByDate(LocalDate invoiceDate);
}
