package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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
        this.calculate();
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

    public void calculate () {
        // Calculate Services
        for (Service s : this.stay.getServices()) {
            this.totalAmount = this.totalAmount.add(s.getServicePrice().price());
        }

        // Calculate RoomCategoryPrices
        List<Season> matchingSeasons = Season.seasons(this.stay.getCheckInDate(), this.stay.getCheckOutDate());
        int nights = Period.between(this.stay.getCheckInDate(), this.stay.getCheckOutDate()).getDays();
        LocalDate tempDate = this.stay.getCheckInDate();
        for(int i = 0; i < nights; i++){
            // TODO: check if tempDate is in Season
            /*
                für x Nächte prüfe:
                    isInSeason(actualDate, season)
                        totalPrice += RoomCategoryPrice.by(category, season)
                        actualDate.plusDays(1)
                    Wenn nicht in Season
                        season = seasons.next
             */
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
