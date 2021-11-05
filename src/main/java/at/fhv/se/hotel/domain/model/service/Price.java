package at.fhv.se.hotel.domain.model.service;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal price;

    // Required by hibernate
    public Price() {
    }

    public Price (BigDecimal aPrice){
        this.price = aPrice;
    }

    public BigDecimal price(){
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
