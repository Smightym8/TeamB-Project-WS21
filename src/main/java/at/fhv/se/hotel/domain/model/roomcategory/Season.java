package at.fhv.se.hotel.domain.model.roomcategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                    LocalDate.of(checkInDate.getYear() + 1, s.endDate.getMonth(), s.endDate.getDayOfMonth()) :
                    LocalDate.of(checkInDate.getYear(), s.endDate.getMonth(), s.endDate.getDayOfMonth());
            if(isOverLapping(adjustedSeasonStart, adjustedSeasonEnd, checkInDate, checkOutDate)) {
                seasons.add(s);
            }
        }

        return seasons;
    }

    private static boolean isOverLapping (LocalDate seasonStart, LocalDate seasonEnd,
                                   LocalDate checkInDate, LocalDate checkOutDate) {

        return (checkInDate.equals(seasonStart) ||
                checkOutDate.equals(seasonEnd) ||
                (checkInDate.isAfter(seasonStart) && checkInDate.isBefore(seasonEnd)) ||
                (checkOutDate.isAfter(seasonStart) && checkOutDate.isBefore(seasonEnd))
        );
    }
}
