package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestCreationService;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

/**
 * This class contains the functionality to create and to save a new guest.
 */
@Component
public class GuestCreationServiceImpl implements GuestCreationService {
    @Autowired
    GuestRepository guestRepository;

    /**
     * This method creates a new guest and saves the guest into the database.
     * @param firstName contains the first name of the guest.
     * @param lastName contains the last name of the guest.
     * @param email contains the email of the guest.
     * @param telephone contains the telephone number of the guest.
     * @param birthDate contains the birthdate of the guest.
     * @param streetName contains the streetname of the guest.
     * @param streetNumber contains the streetnumber of the guest.
     * @param zipCode contains the zipcode of the guest.
     * @param city contains the city of the guest.
     * @param country contains the country of the guest.
     */
    @Transactional
    @Override
    public void createGuest(
            String firstName,
            String lastName,
            String email,
            String telephone,
            LocalDate birthDate,
            String streetName,
            String streetNumber,
            String zipCode,
            String city,
            String country
    ) {
        Guest guest = Guest.create(
                guestRepository.nextIdentity(),
                new FullName(firstName, lastName),
                new Address(streetName, streetNumber, city, zipCode, country),
                birthDate,
                telephone,
                email,
                Collections.emptyList()
        );

        guestRepository.add(guest);
    }
}
