package at.fhv.se.hotel.domain.model.invoice;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

// TODO: Test
public class Invoice {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private InvoiceId invoiceId;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private Stay stay;
    private List<RoomCategoryPrice> roomCategoryPriceList;
    private List<Service> services;
    private int amountOfNights;
    private BigDecimal localTaxPerPerson;
    private BigDecimal localTaxTotal;
    private BigDecimal valueAddedTaxInPercent;
    private BigDecimal valueAddedTaxInEuro;
    private BigDecimal totalNetAmountBeforeDiscount;
    private BigDecimal totalNetAmountAfterDiscount;
    private BigDecimal totalNetAmountAfterLocalTax;
    private BigDecimal totalGrossAmount;
    private boolean isPaid;

    // TODO: paymentMethod

    // Required by hibernate
    @SuppressWarnings("unused")
    private Invoice() {
    }

    public static Invoice create(
            InvoiceId anInvoiceId,
            String anInvoiceNumber,
            Stay aStay,
            List<RoomCategoryPrice> aRoomCategoryPriceList,
            List<Service> aServiceList,
            int anAmountOfNights,
            BigDecimal aLocalTaxPerPerson,
            BigDecimal aLocalTaxTotal,
            BigDecimal aValueAddedTaxInPercent,
            BigDecimal aValueAddedTaxInEuro,
            BigDecimal aTotalNetAmountBeforeDiscount,
            BigDecimal aTotalNetAmountAfterDiscount,
            BigDecimal aTotalNetAmountAfterLocalTax,
            BigDecimal aTotalGrossAmount
    ) {
        return new Invoice(
                anInvoiceId,
                anInvoiceNumber,
                LocalDate.now(),
                aStay,
                aRoomCategoryPriceList,
                aServiceList,
                anAmountOfNights,
                aLocalTaxPerPerson,
                aLocalTaxTotal,
                aValueAddedTaxInPercent,
                aValueAddedTaxInEuro,
                aTotalNetAmountBeforeDiscount,
                aTotalNetAmountAfterDiscount,
                aTotalNetAmountAfterLocalTax,
                aTotalGrossAmount
        );
    }

    private Invoice(
            InvoiceId anInvoiceId,
            String anInvoiceNumber,
            LocalDate invoiceDate,
            Stay aStay,
            List<RoomCategoryPrice> aRoomCategoryPriceList,
            List<Service> aServiceList,
            int anAmountOfNights,
            BigDecimal aLocalTaxPerPerson,
            BigDecimal aLocalTaxTotal,
            BigDecimal aValueAddedTaxInPercent,
            BigDecimal aValueAddedTaxInEuro,
            BigDecimal aTotalNetAmountBeforeDiscount,
            BigDecimal aTotalNetAmountAfterDiscount,
            BigDecimal aTotalNetAmountAfterLocalTax,
            BigDecimal aTotalGrossAmount
    ) {
        this.invoiceId = anInvoiceId;
        this.invoiceNumber = anInvoiceNumber;
        this.invoiceDate = invoiceDate;
        this.stay = aStay;
        this.roomCategoryPriceList = aRoomCategoryPriceList;
        this.services = aServiceList;
        this.amountOfNights = anAmountOfNights;
        this.localTaxPerPerson = aLocalTaxPerPerson;
        this.localTaxTotal = aLocalTaxTotal;
        this.valueAddedTaxInPercent = aValueAddedTaxInPercent;
        this.valueAddedTaxInEuro = aValueAddedTaxInEuro;
        this.totalNetAmountBeforeDiscount = aTotalNetAmountBeforeDiscount;
        this.totalNetAmountAfterDiscount = aTotalNetAmountAfterDiscount;
        this.totalNetAmountAfterLocalTax = aTotalNetAmountAfterLocalTax;
        this.totalGrossAmount = aTotalGrossAmount;
        this.isPaid = false;
    }

    public Long getId() {
        return id;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Stay getStay() {
        return stay;
    }

    public List<RoomCategoryPrice> getRoomCategoryPriceList() {
        return roomCategoryPriceList;
    }

    public List<Service> getServices() {
        return services;
    }

    public int getAmountOfNights() {
        return amountOfNights;
    }

    public BigDecimal getLocalTaxPerPerson() {
        return localTaxPerPerson;
    }

    public BigDecimal getLocalTaxTotal() {
        return localTaxTotal;
    }

    public BigDecimal getValueAddedTaxInPercent() {
        return valueAddedTaxInPercent;
    }

    public BigDecimal getValueAddedTaxInEuro() {
        return valueAddedTaxInEuro;
    }

    public BigDecimal getTotalNetAmountBeforeDiscount() {
        return totalNetAmountBeforeDiscount;
    }

    public BigDecimal getTotalNetAmountAfterDiscount() {
        return totalNetAmountAfterDiscount;
    }

    public BigDecimal getTotalNetAmountAfterLocalTax() {
        return totalNetAmountAfterLocalTax;
    }

    public BigDecimal getTotalGrossAmount() {
        return totalGrossAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    @Generated
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return amountOfNights == invoice.amountOfNights && isPaid == invoice.isPaid && Objects.equals(id, invoice.id) && Objects.equals(invoiceId, invoice.invoiceId) && Objects.equals(invoiceNumber, invoice.invoiceNumber) && Objects.equals(invoiceDate, invoice.invoiceDate) && Objects.equals(stay, invoice.stay) && Objects.equals(roomCategoryPriceList, invoice.roomCategoryPriceList) && Objects.equals(services, invoice.services) && Objects.equals(localTaxPerPerson, invoice.localTaxPerPerson) && Objects.equals(localTaxTotal, invoice.localTaxTotal) && Objects.equals(valueAddedTaxInPercent, invoice.valueAddedTaxInPercent) && Objects.equals(valueAddedTaxInEuro, invoice.valueAddedTaxInEuro) && Objects.equals(totalNetAmountBeforeDiscount, invoice.totalNetAmountBeforeDiscount) && Objects.equals(totalNetAmountAfterDiscount, invoice.totalNetAmountAfterDiscount) && Objects.equals(totalNetAmountAfterLocalTax, invoice.totalNetAmountAfterLocalTax) && Objects.equals(totalGrossAmount, invoice.totalGrossAmount);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, invoiceNumber, invoiceDate, stay, roomCategoryPriceList, services, amountOfNights, localTaxPerPerson, localTaxTotal, valueAddedTaxInPercent, valueAddedTaxInEuro, totalNetAmountBeforeDiscount, totalNetAmountAfterDiscount, totalNetAmountAfterLocalTax, totalGrossAmount, isPaid);
    }
}
