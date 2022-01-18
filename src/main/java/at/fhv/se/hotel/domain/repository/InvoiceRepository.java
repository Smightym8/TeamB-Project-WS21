package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.infrastructure.HibernateInvoiceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get invoice objects from the database.
 * The implementation is in {@link HibernateInvoiceRepository}
 */
public interface InvoiceRepository {

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#findAllInvoices()}
     */
    List<Invoice> findAllInvoices();

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#nextIdentity()}
     */
    InvoiceId nextIdentity();

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#add(Invoice)}
     */
    void add(Invoice invoice);

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#invoiceById(InvoiceId)}
     */
    Optional<Invoice> invoiceById(InvoiceId invoiceId);

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#invoiceByNumber(String)}
     */
    Optional<Invoice> invoiceByNumber(String invoiceNumber);

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#invoicesByDate(LocalDate)}
     */
    List<Invoice> invoicesByDate(LocalDate invoiceDate);

    /**
     * See implementation
     * {@link HibernateInvoiceRepository#amountOfInvoicesByDate(LocalDate)}
     */
    int amountOfInvoicesByDate(LocalDate invoiceDate);
}
