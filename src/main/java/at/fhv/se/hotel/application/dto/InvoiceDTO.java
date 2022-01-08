package at.fhv.se.hotel.application.dto;


import at.fhv.se.hotel.application.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@XmlRootElement(name = "invoice")
public class InvoiceDTO {
    private String stayId;

    private String invoiceId;

    @XmlElement(name = "invoiceNumber")
    private String invoiceNumber;

    @XmlElement(name = "invoiceDate")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate invoiceDate;

    @XmlElement(name = "guestFirstName")
    private String guestFirstName;

    @XmlElement(name = "guestLastName")
    private String guestLastName;

    @XmlElement(name = "streetName")
    private String streetName;

    @XmlElement(name = "streetNumber")
    private String streetNumber;

    @XmlElement(name = "zipCode")
    private String zipCode;

    @XmlElement(name = "city")
    private String city;

    @XmlElement(name = "amountOfAdults")
    private int amountOfAdults;

    @XmlElement(name = "amountOfChildren")
    private int amountOfChildren;

    @XmlElement(name = "service")
    private Map<String, BigDecimal> services;

    @XmlElement(name = "roomNames")
    private List<String> roomNames;

    @XmlElement(name = "checkInDate")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate checkInDate;

    @XmlElement(name = "checkOutDate")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate checkOutDate;

    @XmlElement(name = "amountOfNights")
    private int amountOfNights;

    @XmlElement(name = "localTaxPerPerson")
    private BigDecimal localTaxPerPerson;

    @XmlElement(name = "localTaxTotal")
    private BigDecimal localTaxTotal;

    @XmlElement(name = "valueAddedTaxInPercent")
    private BigDecimal valueAddedTaxInPercent;

    @XmlElement(name = "valueAddedTaxInEuro")
    private BigDecimal valueAddedTaxInEuro;

    @XmlElement(name = "totalNetAmountBeforeDiscount")
    private BigDecimal totalNetAmountBeforeDiscount;

    @XmlElement(name = "totalNetAmountAfterDiscount")
    private BigDecimal totalNetAmountAfterDiscount;

    @XmlElement(name = "totalNetAmountAfterLocalTax")
    private BigDecimal totalNetAmountAfterLocalTax;

    @XmlElement(name = "totalGrossAmount")
    private BigDecimal totalGrossAmount;

    @XmlElement(name = "discountInPercent")
    private double discountInPercent;

    @XmlElement(name = "discountInEuro")
    private BigDecimal discountInEuro;

    @XmlElementWrapper(name = "categoryNames")
    @XmlElement(name = "categoryName")
    private List<String> categoryNames;

    @XmlElementWrapper(name = "categoryPrices")
    @XmlElement(name = "categoryPrice")
    private List<BigDecimal> categoryPrices;

    public static Builder builder() {
        return new Builder();
    }

    public String stayId() {
        return stayId;
    }

    public String invoiceId() {
        return invoiceId;
    }

    public String invoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate invoiceDate() {
        return invoiceDate;
    }

    public String guestFirstName() {
        return guestFirstName;
    }

    public String guestLastName() {
        return guestLastName;
    }

    public String streetName() {
        return streetName;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String zipCode() {
        return zipCode;
    }

    public String city() {
        return city;
    }

    public int amountOfAdults() {
        return amountOfAdults;
    }

    public int amountOfChildren() {
        return amountOfChildren;
    }

    public Map<String, BigDecimal> services() {
        return services;
    }

    public List<String> roomNames() {
        return roomNames;
    }

    public LocalDate checkInDate() {
        return checkInDate;
    }

    public LocalDate checkOutDate() {
        return checkOutDate;
    }

    public int amountOfNights() {
        return amountOfNights;
    }

    public BigDecimal localTaxPerPerson() {
        return localTaxPerPerson;
    }

    public BigDecimal localTaxTotal() {
        return localTaxTotal;
    }

    public BigDecimal valueAddedTaxInPercent() {
        return valueAddedTaxInPercent;
    }

    public BigDecimal valueAddedTaxInEuro() {
        return valueAddedTaxInEuro;
    }

    public BigDecimal totalNetAmountBeforeDiscount() {
        return totalNetAmountBeforeDiscount;
    }

    public BigDecimal totalNetAmountAfterDiscount() {
        return totalNetAmountAfterDiscount;
    }

    public BigDecimal totalNetAmountAfterLocalTax() {
        return totalNetAmountAfterLocalTax;
    }

    public BigDecimal totalGrossAmount() {
        return totalGrossAmount;
    }

    public double discountInPercent() {
        return discountInPercent;
    }

    public BigDecimal discountInEuro() {
        return discountInEuro;
    }

    public List<String> categoryNames() {
        return categoryNames;
    }

    public List<BigDecimal> categoryPrices() {
        return categoryPrices;
    }

    public static class Builder {
        private final InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withStayId(String stayId) {
            this.instance.stayId = stayId;
            return this;
        }

        public Builder withInvoiceId(String invoiceId) {
            this.instance.invoiceId = invoiceId;
            return this;
        }

        public Builder withInvoiceNumber(String invoiceNumber) {
            this.instance.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder withInvoiceDate(LocalDate invoiceDate) {
            this.instance.invoiceDate = invoiceDate;
            return this;
        }

        public Builder withGuestFirstName(String guestFirstName) {
            this.instance.guestFirstName = guestFirstName;
            return this;
        }

        public Builder withGuestLastName(String guestLastName) {
            this.instance.guestLastName = guestLastName;
            return this;
        }

        public Builder withStreetName(String streetName) {
            this.instance.streetName = streetName;
            return this;
        }

        public Builder withStreetNumber(String streetNumber) {
            this.instance.streetNumber = streetNumber;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            this.instance.zipCode = zipCode;
            return this;
        }

        public Builder withCity(String city) {
            this.instance.city = city;
            return this;
        }

        public Builder withAmountOfAdults(int amountOfAdults) {
            this.instance.amountOfAdults = amountOfAdults;
            return this;
        }

        public Builder withAmountOfChildren(int amountOfChildren) {
            this.instance.amountOfChildren = amountOfChildren;
            return this;
        }

        public Builder withServices(Map<String, BigDecimal> services) {
            this.instance.services = services;
            return this;
        }

        public Builder withRoomNames(List<String> roomNames) {
            this.instance.roomNames = roomNames;
            return this;
        }

        public Builder withCheckInDate(LocalDate checkInDate) {
            this.instance.checkInDate = checkInDate;
            return this;
        }

        public Builder withCheckOutDate(LocalDate checkOutDate) {
            this.instance.checkOutDate = checkOutDate;
            return this;
        }

        public Builder withAmountOfNights(int amountOfNights) {
            this.instance.amountOfNights = amountOfNights;
            return this;
        }

        public Builder withLocalTaxPerPerson(BigDecimal localTaxPerPerson) {
            this.instance.localTaxPerPerson = localTaxPerPerson;
            return this;
        }

        public Builder withLocalTaxTotal(BigDecimal localTaxTotal) {
            this.instance.localTaxTotal = localTaxTotal;
            return this;
        }

        public Builder withValueAddedTaxInPercent(BigDecimal valueAddedTaxInPercent) {
            this.instance.valueAddedTaxInPercent = valueAddedTaxInPercent;
            return this;
        }

        public Builder withValueAddedTaxInEuro(BigDecimal valueAddedTaxInEuro) {
            this.instance.valueAddedTaxInEuro = valueAddedTaxInEuro;
            return this;
        }

        public Builder withTotalNetAmountBeforeDiscount(BigDecimal totalNetAmountBeforeDiscount) {
            this.instance.totalNetAmountBeforeDiscount = totalNetAmountBeforeDiscount;
            return this;
        }

        public Builder withTotalNetAmountAfterDiscount(BigDecimal totalNetAmountAfterDiscount) {
            this.instance.totalNetAmountAfterDiscount = totalNetAmountAfterDiscount;
            return this;
        }

        public Builder withTotalNetAmountAfterLocalTax(BigDecimal totalNetAmountAfterLocalTax) {
            this.instance.totalNetAmountAfterLocalTax = totalNetAmountAfterLocalTax;
            return this;
        }

        public Builder withTotalGrossAmount(BigDecimal totalGrossAmount) {
            this.instance.totalGrossAmount = totalGrossAmount;
            return this;
        }

        public Builder withDiscountInPercent(double discountInPercent) {
            this.instance.discountInPercent = discountInPercent;
            return this;
        }

        public Builder withDiscountInEuro(BigDecimal discountInEuro) {
            this.instance.discountInEuro = discountInEuro;
            return this;
        }

        public Builder withCategoryNames(List<String> categoryNames) {
            this.instance.categoryNames = categoryNames;
            return this;
        }

        public Builder withCategoryPrices(List<BigDecimal> categoryPrices) {
            this.instance.categoryPrices = categoryPrices;
            return this;
        }


        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.invoiceNumber, "invoiceNumber must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.invoiceDate, "invoiceDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.roomNames, "roomNames must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.localTaxPerPerson, "localTaxPerPerson must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.localTaxTotal, "localTaxTotal must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.valueAddedTaxInPercent, "valueAddedTaxInPercent must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.valueAddedTaxInEuro, "valueAddedTaxInEuro must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalNetAmountBeforeDiscount, "totalNetAmountBeforeDiscount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalNetAmountAfterDiscount, "totalNetAmountAfterDiscount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalNetAmountAfterLocalTax, "totalNetAmountAfterLocalTax must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalGrossAmount, "totalGrossAmount must be set in InvoiceDTO");

            return this.instance;
        }
    }
}
