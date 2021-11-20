package at.fhv.se.hotel.application.api;


import at.fhv.se.hotel.application.impl.GuestCreationServiceImpl;

import java.time.LocalDate;

/**
 * This class represents an interface that defines the functionality to create a guest.
 * The implementation is in {@link GuestCreationServiceImpl}.
 */
public interface GuestCreationService {
    /**
     * See implementation {@link GuestCreationServiceImpl#createGuest(String, String, String, String, String, LocalDate,
     * String, String, String, String, String)}
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
     */
    void createGuest(
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
            String country
    );
}
