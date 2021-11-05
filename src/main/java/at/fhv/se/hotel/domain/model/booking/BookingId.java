package at.fhv.se.hotel.domain.model.booking;

public class BookingId {
    private String id;

    // Required by hibernate
    public BookingId() {}

    public BookingId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
