package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestModifyService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * This class represents the implementation of the interface {@link GuestModifyService}
 * It provides the functionality to modify a guest.
 */
@Component
public class GuestModifyServiceImpl implements GuestModifyService {
    @Autowired
    private GuestRepository guestRepository;

    /**
     * This method provides the functionality to modify a guest.
     * @param guestId contains the id of the guest.
     * @param firstName contains the first name of the guest.
     * @param lastName contains the last name of the guest.
     * @param gender contains the gender of the guest.
     * @param streetName contains the streetname of the guest.
     * @param streetNumber contains the streetnumber of the guest.
     * @param zipCode contains the zipcode of the guest.
     * @param city contains the city of the guest.
     * @param country contains the country of the guest.
     * @param birthDate contains the birthdate of the guest.
     * @param phoneNumber contains the phone number of the guest.
     * @param mailAddress contains the mail address of the guest.
     * @param discount contains the discount of the guest.
     * @throws GuestNotFoundException if the guest could not be found.
     */
    @Transactional
    @Override
    public void modifyGuest(String guestId, String firstName, String lastName, String gender, String streetName,
                            String streetNumber, String city, String zipCode, String country, LocalDate birthDate,
                            String phoneNumber, String mailAddress, double discount) throws GuestNotFoundException {

        Guest guest = guestRepository.guestById(new GuestId(guestId)).orElseThrow(
                () -> new GuestNotFoundException("Guest with id " + guestId + " not found")
        );

        guest.modify(
                new FullName(firstName, lastName),
                Gender.valueOf(gender.toUpperCase()),
                new Address(streetName, streetNumber, city, zipCode, country),
                birthDate,
                phoneNumber,
                mailAddress,
                discount
        );
    }
}
