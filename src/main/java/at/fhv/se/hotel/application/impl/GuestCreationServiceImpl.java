package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestCreationService;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;

/**
 * This class represents the implementation of the interface {@link GuestCreationService}
 * It contains the functionality to create and to save a new guest.
 */
@Component
public class GuestCreationServiceImpl implements GuestCreationService {
    @Autowired
    private GuestRepository guestRepository;

    /**
     * This method creates a new guest and saves the guest into the database.
     * @param firstName contains the first name of the guest.
     * @param lastName contains the last name of the guest.
     * @param gender contains the gender of the guest.
     * @param email contains the email of the guest.
     * @param telephone contains the telephone number of the guest.
     * @param birthDate contains the birthdate of the guest.
     * @param streetName contains the streetname of the guest.
     * @param streetNumber contains the streetnumber of the guest.
     * @param zipCode contains the zipcode of the guest.
     * @param city contains the city of the guest.
     * @param country contains the country of the guest.
     * @param discountInPercent contains the discount in percent for special guests.
     */
    @Transactional
    @Override
    public String createGuest(
            String firstName,
            String lastName,
            String gender,
            String email,
            String telephone,
            LocalDate birthDate,
            String streetName,
            String streetNumber,
            String zipCode,
            String city,
            String country,
            double discountInPercent
    ) {
        GuestId guestId = guestRepository.nextIdentity();

        Guest guest = Guest.create(
                guestId,
                new FullName(firstName, lastName),
                Gender.valueOf(gender.toUpperCase(Locale.ROOT)),
                new Address(streetName, streetNumber, city, zipCode, country),
                birthDate,
                telephone,
                email,
                discountInPercent,
                Collections.emptyList()
        );

        guestRepository.add(guest);

        return guestId.id();
    }
}
