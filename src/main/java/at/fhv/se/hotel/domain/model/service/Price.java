package at.fhv.se.hotel.domain.model.service;

import java.math.BigDecimal;

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
}
