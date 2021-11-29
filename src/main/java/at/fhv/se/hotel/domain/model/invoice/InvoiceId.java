package at.fhv.se.hotel.domain.model.invoice;

import java.util.Objects;

public class InvoiceId {
    private String id;

    // required by hibernate
    private InvoiceId() {
    }

    public InvoiceId(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceId invoiceId = (InvoiceId) o;
        return Objects.equals(id, invoiceId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
