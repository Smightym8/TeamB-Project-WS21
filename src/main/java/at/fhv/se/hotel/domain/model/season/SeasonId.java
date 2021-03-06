package at.fhv.se.hotel.domain.model.season;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;
/**
 * This class is a value object for the season which contains the id
 */
public class SeasonId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private SeasonId() {
    }

    public SeasonId(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonId seasonId = (SeasonId) o;
        return Objects.equals(id, seasonId.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
