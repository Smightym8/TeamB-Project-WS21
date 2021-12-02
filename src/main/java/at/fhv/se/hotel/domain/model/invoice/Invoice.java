package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.model.stay.Stay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice {
    // Required by hibernate
    private Long id;
    private InvoiceId invoiceId;
    private LocalDate invoiceDate;
    private Stay stay;
    private int amountOfNights;
    private BigDecimal totalAmount;

    // TODO: InvoiceNr (e.g. 20210112001), localTax, valueAddedTax, Prices per RoomCategory, paymentMethod

    // Required by hibernate
    private Invoice() {
    }

    public static Invoice create(InvoiceId anInvoiceId, Stay aStay, int anAmountOfNights, BigDecimal aAmount) {

        return new Invoice(anInvoiceId, LocalDate.now(), aStay, anAmountOfNights, aAmount);
    }

    private Invoice(InvoiceId anInvoiceId, LocalDate invoiceDate, Stay aStay, int anAmountOfNights, BigDecimal aAmount) {
        this.invoiceId = anInvoiceId;
        this.invoiceDate = invoiceDate;
        this.stay = aStay;
        this.amountOfNights = anAmountOfNights;
        this.totalAmount = aAmount;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Stay getStay() {
        return stay;
    }

    public int getAmountOfNights() {
        return amountOfNights;
    }

    public BigDecimal getTotalAmount(){
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return amountOfNights == invoice.amountOfNights && Objects.equals(id, invoice.id) && Objects.equals(invoiceId, invoice.invoiceId) && Objects.equals(stay, invoice.stay) && Objects.equals(totalAmount, invoice.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, stay, amountOfNights, totalAmount);
    }
}
