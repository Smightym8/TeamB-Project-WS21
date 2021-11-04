package at.fhv.se.hotel.domain.models.RoomCategory;

public class Description {
    private String description;

    public Description(String aDescription){
        this.description = aDescription;
    }

    public String description() {
        return this.description;
    }
}
