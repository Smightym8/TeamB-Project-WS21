package at.fhv.se.hotel.domain.model.roomcategory;

import java.util.Objects;

public class Description {
    private String description;

    // Required by hibernate
    private Description() {
    }

    public Description(String aDescription){
        this.description = aDescription;
    }

    public String description() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
