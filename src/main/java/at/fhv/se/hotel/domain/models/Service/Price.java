package at.fhv.se.hotel.domain.models.Service;

public class Price {
    private int price;

    public Price (int aPrice){
        this.price = aPrice;
    }

    public int price(){
        return this.price;
    }
}
