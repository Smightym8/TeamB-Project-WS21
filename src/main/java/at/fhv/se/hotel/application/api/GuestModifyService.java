package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;

import java.time.LocalDate;

public interface GuestModifyService {
    /**
     * See implementation
     * {@link at.fhv.se.hotel.application.impl.GuestModifyServiceImpl#modifyGuest(
     * String, String, String, String, String, String, String, String, String, LocalDate, String, String, double)}
     */
    void modifyGuest(
            String guestId,
            String firstName,
            String lastName,
            String gender,
            String streetName,
            String streetNumber,
            String city,
            String zipCode,
            String country,
            LocalDate birthDate,
            String phoneNumber,
            String mailAddress,
            double discount
    ) throws GuestNotFoundException;
}
