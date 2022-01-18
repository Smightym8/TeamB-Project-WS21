package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.dto.GuestDetailsDTO;
import at.fhv.se.hotel.application.dto.GuestListingDTO;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link GuestListingService}
 * It provides the functionality to get all guests.
 */
@Component
public class GuestListingServiceImpl implements GuestListingService {

    @Autowired
    GuestRepository guestRepository;

    /**
     * This method provides all bookings.
     * @return a list of GuestListingDTO objects.
     */
    @Transactional(readOnly=true)
    @Override
    public List<GuestListingDTO> allGuests() {
        List<Guest> guests = guestRepository.findAllGuests();
        List<GuestListingDTO> dtos = new ArrayList<>();

        for(Guest guest : guests) {
            GuestListingDTO dto = GuestListingDTO.builder()
                    .withId(guest.getGuestId().id())
                    .withFirstName(guest.getName().firstName())
                    .withLastName(guest.getName().lastName())
                    .withStreetName(guest.getAddress().streetName())
                    .withStreetNumber(guest.getAddress().streetNumber())
                    .withCity(guest.getAddress().city())
                    .withZipCode(guest.getAddress().zipCode())
                    .withCountry(guest.getAddress().country())
                    .withMailAddress(guest.getMailAddress())
                    .withPhoneNumber(guest.getPhoneNumber())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * This method provides details of a guest by id.
     * @param id contains the id of the guest.
     * @return a GuestDetailsDTO object.
     * @throws GuestNotFoundException if the guest could not be found.
     */
    @Override
    public GuestDetailsDTO findGuestById(String id) throws GuestNotFoundException {
        Guest guest = guestRepository.guestById(new GuestId(id)).orElseThrow(
                () -> new GuestNotFoundException("Guest with id " + id + " not found")
        );

        GuestDetailsDTO guestDetailsDTO = GuestDetailsDTO.builder()
                .withId(guest.getGuestId().id())
                .withFirstName(guest.getName().firstName())
                .withLastName(guest.getName().lastName())
                .withGender(guest.getGender().name())
                .withStreetName(guest.getAddress().streetName())
                .withStreetNumber(guest.getAddress().streetNumber())
                .withCity(guest.getAddress().city())
                .withZipCode(guest.getAddress().zipCode())
                .withCountry(guest.getAddress().country())
                .withMailAddress(guest.getMailAddress())
                .withPhoneNumber(guest.getPhoneNumber())
                .withBirthDate(guest.getBirthDate())
                .withDiscount(guest.getDiscountInPercent())
                .build();

        return guestDetailsDTO;
    }
}
