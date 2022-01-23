package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link InvoiceRepository}.
 * It provides the functionality to
 * get all invoices
 * generate a new invoice id
 * save an invoice in the database
 * get an invoice by id
 * get an invoice by number
 * get an invoice by date
 * get the amount of invoices for a specific date
 */
@Component
public class HibernateInvoiceRepository implements InvoiceRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all invoices from the database
     * @return a list with all invoices
     */
    @Override
    public List<Invoice> findAllInvoices() {
        TypedQuery<Invoice> query = this.em.createQuery("SELECT i FROM Invoice i", Invoice.class);
        return query.getResultList();
    }

    /**
     * This method generates a new invoice id
     * @return the generated invoice id
     */
    @Override
    public InvoiceId nextIdentity() {
        return new InvoiceId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves an invoice into the database
     * @param invoice contains the invoice which will be saved
     */
    @Override
    public void add(Invoice invoice) {
        this.em.persist(invoice);
    }

    /**
     * This method gets an invoice by id
     * @param invoiceId contains the id of the invoice
     * @return the invoice with the provided id
     */
    @Override
    public Optional<Invoice> invoiceById(InvoiceId invoiceId) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceId = :invoiceId", Invoice.class);
        query.setParameter("invoiceId", invoiceId);
        return query.getResultStream().findFirst();
    }

    /**
     * This method gets an invoice by number
     * @param invoiceNumber contains the number of the invoice
     * @return the invoice with the provided number
     */
    @Override
    public Optional<Invoice> invoiceByNumber(String invoiceNumber) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceNumber = :invoiceNumber", Invoice.class);
        query.setParameter("invoiceNumber", invoiceNumber);
        return query.getResultStream().findFirst();
    }

    /**
     * This method gets all invoices that were created on a specific date
     * @param invoiceDate contains the date of the invoices
     * @return a list with invoices that were created on the provided date
     */
    @Override
    public List<Invoice> invoicesByDate(LocalDate invoiceDate) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceDate = :invoiceDate", Invoice.class);
        query.setParameter("invoiceDate", invoiceDate);
        return query.getResultList();
    }

    /**
     * This method determines the amount of invoices that were created on a specific date
     * @param invoiceDate contains the date of the invoices
     * @return the amount of invoices for the provided date
     */
    @Override
    public int amountOfInvoicesByDate(LocalDate invoiceDate) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceDate = :invoiceDate", Invoice.class);
        query.setParameter("invoiceDate", invoiceDate);
        return query.getResultList().size();
    }
}
