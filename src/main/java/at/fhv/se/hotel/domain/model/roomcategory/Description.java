package at.fhv.se.hotel.domain.model.roomcategory;

public class Description {
    private String description;

    // Required by hibernate
    public Description() {
    }

    public Description(String aDescription){
        this.description = aDescription;
    }

    public String description() {
        return this.description;
    }
}
