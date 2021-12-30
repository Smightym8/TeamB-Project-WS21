package at.fhv.se.hotel.domain.model.roomcategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: Replace with SeasonNew
// Identify usages of this season class and use SeasonNew instead
// After everything is replaced delete this class and use only SeasonNew
@Deprecated
public enum Season {
    WINTER (LocalDate.of(2000, 12, 1), LocalDate.of(2000, 1, 31)),
    SPRING (LocalDate.of(2000, 2, 1), LocalDate.of(2000, 5, 31)),
    SUMMER (LocalDate.of(2000, 6, 1), LocalDate.of(2000, 11, 30));

    private final LocalDate startDate;
    private final LocalDate endDate;

    Season(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public static List<Season> seasons(LocalDate checkInDate, LocalDate checkOutDate) {
        List<Season> seasons = new ArrayList<>();

        for(Season s : Season.values()) {
            // Adjust season dates, because seasons are every year at the same time
            LocalDate adjustedSeasonStart = LocalDate.of(checkInDate.getYear(), s.startDate.getMonth(), s.startDate.getDayOfMonth());
            // Because 31.01 is before 01.12 in the same year, add 1 year to the winter end date
            LocalDate adjustedSeasonEnd = s.equals(Season.WINTER) ?
                    LocalDate.of(checkInDate.getYear(), s.endDate.getMonth(), s.endDate.getDayOfMonth()).plusYears(1) :
                    LocalDate.of(checkInDate.getYear(), s.endDate.getMonth(), s.endDate.getDayOfMonth());
            if(isOverLapping(adjustedSeasonStart, adjustedSeasonEnd, checkInDate, checkOutDate)) {
                seasons.add(s);
            }
        }

        return seasons;
    }

    private static boolean isOverLapping (LocalDate seasonStart, LocalDate seasonEnd,
                                   LocalDate checkInDate, LocalDate checkOutDate) {

        return (checkInDate.isEqual(seasonStart) ||
                checkInDate.isEqual(seasonEnd) ||
                checkOutDate.isEqual(seasonStart) ||
                checkOutDate.isEqual(seasonEnd) ||
                // Check if booking range is in the range of the season
                (checkInDate.isAfter(seasonStart) && checkInDate.isBefore(seasonEnd)) ||
                (checkOutDate.isAfter(seasonStart) && checkOutDate.isBefore(seasonEnd)) ||
                // Check if season range is in the range of the booking
                (seasonStart.isAfter(checkInDate) && seasonStart.isBefore(checkOutDate)) ||
                (seasonEnd.isAfter(checkInDate) && seasonEnd.isBefore(checkOutDate))
        );
    }

    public static Season seasonByDate(LocalDate date) {
        Season season = null;
        for(Season s : Season.values()) {
            if(isInSeason(date, s)) {
                season = s;
            }
        }

        return season;
    }

    public static boolean isInSeason (LocalDate currentDate, Season season){

        LocalDate adjustedSeasonStart = season.equals(Season.WINTER) &&
                (currentDate.getMonth().equals(Season.WINTER.endDate.getMonth())) ?
                LocalDate.of(currentDate.getYear(), season.startDate.getMonth(), season.startDate.getDayOfMonth()).plusYears(-1) :
                LocalDate.of(currentDate.getYear(), season.startDate.getMonth(), season.startDate.getDayOfMonth());

        // Because 31.01 is before 01.12 in the same year, add 1 year to the winter end date.
        // But check if currentDate is not in the january because then winter season end has to have the same year
        LocalDate adjustedSeasonEnd = season.equals(Season.WINTER) &&
                (!currentDate.getMonth().equals(Season.WINTER.endDate.getMonth())) ?
                LocalDate.of(currentDate.getYear(), season.endDate.getMonth(), season.endDate.getDayOfMonth()).plusYears(1) :
                LocalDate.of(currentDate.getYear(), season.endDate.getMonth(), season.endDate.getDayOfMonth());

        return (currentDate.isEqual(adjustedSeasonStart) ||
                currentDate.isEqual(adjustedSeasonEnd) ||
                (currentDate.isAfter(adjustedSeasonStart) && currentDate.isBefore(adjustedSeasonEnd))
        );
    }

}
