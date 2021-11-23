package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.model.stay.Stay;

import java.util.Objects;

public class Invoice {
    // Required by hibernate
    private Long id;
    private InvoiceId invoiceId;
    private Stay stay;
    // TODO: invoiceAmount is part of Task 140

    // Required by hibernate
    private Invoice() {
    }

    public static Invoice create(InvoiceId anInvoiceId, Stay aStay) {
        return new Invoice(anInvoiceId, aStay);
    }

    private Invoice(InvoiceId anInvoiceId, Stay aStay) {
        this.invoiceId = anInvoiceId;
        this.stay = aStay;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public Stay getStay() {
        return stay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(invoiceId, invoice.invoiceId) && Objects.equals(stay, invoice.stay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, stay);
    }
}
