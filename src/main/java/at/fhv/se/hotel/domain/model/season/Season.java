package at.fhv.se.hotel.domain.model.season;

import at.fhv.se.hotel.domain.Generated;
import java.time.LocalDate;
import java.util.Objects;

public class Season {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private SeasonId seasonId;
    private SeasonName seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    // Required by hibernate
    @SuppressWarnings("unused")
    protected Season() {
    }

    public static Season create(SeasonId aSeasonId, SeasonName aSeasonName, LocalDate aStartDate, LocalDate aEndDate) {
        return new Season(aSeasonId, aSeasonName, aStartDate, aEndDate);
    }

    private Season(SeasonId aSeasonId, SeasonName aSeasonName, LocalDate aStartDate, LocalDate aEndDate) {
        this.seasonId = aSeasonId;
        this.seasonName = aSeasonName;
        this.startDate = aStartDate;
        this.endDate = aEndDate;
    }

    public SeasonId getSeasonId() {
        return seasonId;
    }

    public SeasonName getSeasonName() {
        return seasonName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return Objects.equals(id, season.id) && Objects.equals(seasonId, season.seasonId) && Objects.equals(seasonName, season.seasonName) && Objects.equals(startDate, season.startDate) && Objects.equals(endDate, season.endDate);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, seasonId, seasonName, startDate, endDate);
    }
}
