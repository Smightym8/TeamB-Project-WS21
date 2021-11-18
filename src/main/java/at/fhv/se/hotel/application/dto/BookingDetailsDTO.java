package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BookingDetailsDTO {
    private String id;
    private GuestDTO guest;
    private Map<RoomCategoryDTO, Integer> categoriesWithAmounts;
    private List<ServiceDTO> services;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public Map<RoomCategoryDTO, Integer> categoriesWithAmounts() {
        return this.categoriesWithAmounts;
    }

    public List<ServiceDTO> services() {
        return this.services;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public LocalDate checkOutDate() {
        return this.checkOutDate;
    }

    public static class Builder {
        private final BookingDetailsDTO instance;

        public Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withGuest(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public Builder withRoomCategoriesAndAmounts(Map<RoomCategoryDTO, Integer> categoriesWithAmounts) {
            this.instance.categoriesWithAmounts = categoriesWithAmounts;
            return this;
        }

        public Builder withServices(List<ServiceDTO> services) {
            this.instance.services = services;
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

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.categoriesWithAmounts,
                    "categoriesWithAmounts must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingDetailsDTO");

            return this.instance;
        }
    }
}
