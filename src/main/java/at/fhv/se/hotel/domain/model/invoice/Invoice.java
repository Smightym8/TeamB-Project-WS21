package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Invoice {
    // Required by hibernate
    private Long id;
    private InvoiceId invoiceId;
    private Stay stay;
    private BigDecimal totalAmount;

    // Required by hibernate
    private Invoice() {
    }

    public static Invoice create(InvoiceId anInvoiceId, Stay aStay, BigDecimal aAmount) {
        return new Invoice(anInvoiceId, aStay, aAmount);
    }

    private Invoice(InvoiceId anInvoiceId, Stay aStay, BigDecimal aAmount) {
        this.invoiceId = anInvoiceId;
        this.stay = aStay;
        this.totalAmount = aAmount;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public Stay getStay() {
        return stay;
    }

    public BigDecimal getTotalAmount(){
        return totalAmount;
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
