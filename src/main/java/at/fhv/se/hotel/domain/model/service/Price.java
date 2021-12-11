package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Objects;

// TODO: Test
public class Price {
    @XmlElement(name = "price")
    private BigDecimal price;

    // Required by hibernate
    private Price() {
    }

    public Price (BigDecimal aPrice){
        this.price = aPrice;
    }

    public BigDecimal price(){
        return this.price;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
