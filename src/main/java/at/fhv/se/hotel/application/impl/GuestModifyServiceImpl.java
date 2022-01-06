package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestModifyService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class GuestModifyServiceImpl implements GuestModifyService {
    @Autowired
    GuestRepository guestRepository;

    /**
     *
     * @param guestId
     * @param firstName
     * @param lastName
     * @param gender
     * @param streetName
     * @param streetNumber
     * @param city
     * @param zipCode
     * @param country
     * @param birthDate
     * @param phoneNumber
     * @param mailAddress
     * @param discount
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
