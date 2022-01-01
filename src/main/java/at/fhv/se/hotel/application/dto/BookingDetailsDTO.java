package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BookingDetailsDTO {
    private String bookingId;
    private String guestId;
    private String guestFirstName;
    private String guestLastName;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;
    private Map<String, Integer> categoriesWithAmounts;
    private List<String> categoryIds;
    private Map<String, BigDecimal> services;
    private List<String> serviceIds;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int amountOfAdults;
    private int amountOfChildren;
    private String additionalInformation;

    public static Builder builder() {
        return new Builder();
    }

    public String bookingId() {
        return this.bookingId;
    }

    public String guestId() {
        return guestId;
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

    public String country() {
        return country;
    }

    public Map<String, Integer> categoriesWithAmounts() {
        return this.categoriesWithAmounts;
    }

    public List<String> categoryIds() {
        return categoryIds;
    }

    public Map<String, BigDecimal> services() {
        return this.services;
    }

    public List<String> serviceIds() {
        return serviceIds;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public LocalDate checkOutDate() {
        return this.checkOutDate;
    }

    public int amountOfAdults() {
        return amountOfAdults;
    }

    public int amountOfChildren() {
        return amountOfChildren;
    }

    public String additionalInformation() { return additionalInformation; };

    public static class Builder {
        private final BookingDetailsDTO instance;

        public Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public Builder withBookingId(String bookingId) {
            this.instance.bookingId = bookingId;
            return this;
        }

        public Builder withGuestId(String guestId) {
            this.instance.guestId = guestId;
            return this;
        }

        public Builder withGuestFirstName(String firstName) {
            this.instance.guestFirstName = firstName;
            return this;
        }

        public Builder withGuestLastName(String lastName) {
            this.instance.guestLastName = lastName;
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

        public Builder withCountry(String country) {
            this.instance.country = country;
            return this;
        }

        public Builder withRoomCategoriesAndAmounts(Map<String, Integer> categoriesWithAmounts) {
            this.instance.categoriesWithAmounts = categoriesWithAmounts;
            return this;
        }

        public Builder withCategoryIds(List<String> categoryIds) {
            this.instance.categoryIds = categoryIds;
            return this;
        }

        public Builder withServices(Map<String, BigDecimal> services) {
            this.instance.services = services;
            return this;
        }

        public Builder withServiceIds(List<String> serviceIds) {
            this.instance.serviceIds = serviceIds;
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

        public Builder withAmountOfAdults(int amountOfAdults) {
            this.instance.amountOfAdults = amountOfAdults;
            return this;
        }

        public Builder withAmountOfChildren(int amountOfChildren) {
            this.instance.amountOfChildren= amountOfChildren;
            return this;
        }

        public Builder withAdditionalInformation(String additionalInformation) {
            this.instance.additionalInformation = additionalInformation;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.country, "country must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.categoriesWithAmounts,
                    "categoriesWithAmounts must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingDetailsDTO");
            
            return this.instance;
        }
    }
}
