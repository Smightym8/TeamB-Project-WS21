package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.season.Season;

import java.math.BigDecimal;
import java.util.Objects;

public class RoomCategoryPrice {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private RoomCategoryPriceId roomCategoryPriceId;
    private Season season;
    private RoomCategory roomCategory;
    private BigDecimal price;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private RoomCategoryPrice(){}

    public static RoomCategoryPrice create(RoomCategoryPriceId aRoomCategoryPriceId, Season aSeason, RoomCategory aRoomCategory, BigDecimal aPrice) {
        return new RoomCategoryPrice(aRoomCategoryPriceId, aSeason, aRoomCategory, aPrice);
    }

    private RoomCategoryPrice(RoomCategoryPriceId aRoomCategoryPriceId, Season aSeason, RoomCategory aRoomCategory, BigDecimal aPrice){
        this.roomCategoryPriceId = aRoomCategoryPriceId;
        this.season = aSeason;
        this.roomCategory = aRoomCategory;
        this.price = aPrice;
    }

    public RoomCategoryPriceId getRoomCategoryPriceId() {
        return roomCategoryPriceId;
    }

    public Season getSeason() {
        return season;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategoryPrice that = (RoomCategoryPrice) o;
        return Objects.equals(id, that.id) && Objects.equals(roomCategoryPriceId, that.roomCategoryPriceId) && season == that.season && Objects.equals(roomCategory, that.roomCategory) && Objects.equals(price, that.price);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, roomCategoryPriceId, season, roomCategory, price);
    }
}
