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

    public static Invoice create(InvoiceId anInvoiceId, Stay aStay) {
        return new Invoice(anInvoiceId, aStay);
    }

    private Invoice(InvoiceId anInvoiceId, Stay aStay) {
        this.invoiceId = anInvoiceId;
        this.stay = aStay;
        this.totalAmount = new BigDecimal("0");
        calculate();
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

    public void calculate() {
        // Calculate Services
        for (Service s : this.stay.getServices()) {
            this.totalAmount = this.totalAmount.add(s.getServicePrice().price());
        }

        // Calculate RoomCategoryPrices
        int nights = Period.between(this.stay.getCheckInDate(), this.stay.getCheckOutDate()).getDays();
        LocalDate tempDate = this.stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(BookingWithRoomCategory brc : stay.getBooking().getRoomCategories()) {
                this.totalAmount = this.totalAmount.add(
                        (
                                new BigDecimal("1")
                              // Get price of current room category for current season
                        ).multiply(new BigDecimal(brc.getAmount()))
                );
            }

            tempDate = tempDate.plusDays(1);
        }
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
