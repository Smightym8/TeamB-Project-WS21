package at.fhv.se.hotel.domain.model.season;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

public class SeasonName {
    private String name;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private SeasonName() {
    }

    public SeasonName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonName that = (SeasonName) o;
        return Objects.equals(name, that.name);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
