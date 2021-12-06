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

@Component
public class HibernateInvoiceRepository implements InvoiceRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Invoice> findAllInvoices() {
        TypedQuery<Invoice> query = this.em.createQuery("SELECT i FROM Invoice i", Invoice.class);
        return query.getResultList();
    }

    @Override
    public InvoiceId nextIdentity() {
        return new InvoiceId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Invoice invoice) {
        this.em.persist(invoice);
    }

    @Override
    public Optional<Invoice> invoiceById(InvoiceId invoiceId) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceId = :invoiceId", Invoice.class);
        query.setParameter("invoiceId", invoiceId);
        return query.getResultStream().findFirst();
    }

    // TODO: Test
    @Override
    public List<Invoice> invoicesByDate(LocalDate invoiceDate) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice AS i WHERE i.invoiceDate = :invoiceDate", Invoice.class);
        query.setParameter("invoiceDate", invoiceDate);
        return query.getResultList();
    }

}
