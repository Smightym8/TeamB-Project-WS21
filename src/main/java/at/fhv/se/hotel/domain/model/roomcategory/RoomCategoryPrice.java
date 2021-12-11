package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Objects;

// TODO: Test
public class RoomCategoryPrice {
    // Required by hibernate
    private Long id;
    private RoomCategoryPriceId roomCategoryPriceId;

    @XmlElement(name = "season")
    private Season season;

    @XmlElement(name = "roomCategory")
    private RoomCategory roomCategory;

    @XmlElement(name = "price")
    private BigDecimal price;

    public static RoomCategoryPrice create(RoomCategoryPriceId aRoomCategoryPriceId, Season aSeason, RoomCategory aRoomCategory, BigDecimal aPrice) {
        return new RoomCategoryPrice(aRoomCategoryPriceId, aSeason, aRoomCategory, aPrice);
    }

    // Required by Hibernate
    private RoomCategoryPrice(){}

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
