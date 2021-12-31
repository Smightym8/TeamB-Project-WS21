package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.SeasonCreationService;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class SeasonCreationServiceImpl implements SeasonCreationService {
    @Autowired
    SeasonRepository seasonRepository;

    @Transactional
    @Override
    public void createSeason(String name, LocalDate startDate, LocalDate endDate) {
        Season season = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName(name),
                startDate,
                endDate
        );

        seasonRepository.add(season);
    }
}
