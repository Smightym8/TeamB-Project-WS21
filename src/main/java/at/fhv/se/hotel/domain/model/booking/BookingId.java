package at.fhv.se.hotel.domain.model.booking;

public class BookingId {
    private String id;

    public BookingId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
