package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

// TODO: Test
public class InvoiceId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private InvoiceId() {
    }

    public InvoiceId(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceId invoiceId = (InvoiceId) o;
        return Objects.equals(id, invoiceId.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
