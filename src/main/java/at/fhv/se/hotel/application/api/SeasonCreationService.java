package at.fhv.se.hotel.application.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import at.fhv.se.hotel.application.impl.SeasonCreationServiceImpl;

/**
 * This class represents an interface that defines the creation of a season.
 * The implementation is in {@link SeasonCreationServiceImpl}.
 */
public interface SeasonCreationService {
    /**
     * See implementation
     * {@link SeasonCreationServiceImpl#createSeason(String, LocalDate, LocalDate, BigDecimal, BigDecimal, BigDecimal, BigDecimal)}.
     */
    void createSeason(String name, LocalDate startDate, LocalDate endDate,
                      BigDecimal singleRoomPrice, BigDecimal doubleRoomPrice,
                      BigDecimal juniorSuitePrice, BigDecimal suitePrice);
}
