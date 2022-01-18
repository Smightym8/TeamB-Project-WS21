package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckOutService;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the implementation of the interface {@link CheckOutService}
 * It provides the functionality of
 * the check out of a stay
 * the creation of an invoice
 * the saving of an invoice into the database.
 */
@Component
public class CheckOutServiceImpl implements CheckOutService {

    @Autowired
    StayRepository stayRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceCreationService invoiceCreationService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    /**
     * This method creates an invoice for a stay.
     * @param stayId contains the id of a stay.
     * @param roomNames contains the names of the rooms to be paid.
     * @param action contains the information so either the stay will be checked out or an invoice will be created.
     * @return an InvoiceDTO object.
     * @throws StayNotFoundException if the stay could not be found.
     * @throws RoomNotFoundException if the room could not be found.
     * @throws SeasonNotFoundException if the season could not be found.
     */
    @Transactional
    @Override
    public InvoiceDTO createInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Creating invoice failed! Stay with id " + stayId + " not found")
        );

        Invoice invoice = invoiceCreationService.createInvoice(stay, roomNames, action);

        Map<String, BigDecimal> services = new HashMap<>();
        for(Service s : invoice.getServices()) {
            services.put(s.getServiceName().name(), s.getServicePrice().price());
        }

        List<String> categoryNames = new ArrayList<>();
        invoice.getRooms().forEach(room -> categoryNames.add(room.getRoomCategory().getRoomCategoryName().name()));

        List<BigDecimal> categoryPrices = new ArrayList<>();
        for (String name : categoryNames) {
            for (RoomCategoryPrice rcp : invoice.getRoomCategoryPriceList()) {
                if (name.equals(rcp.getRoomCategory().getRoomCategoryName().name())) {
                    categoryPrices.add(rcp.getPrice());
                }
            }
        }

        BigDecimal discountInEuro = new BigDecimal("0");

        if (invoice.getStay().getGuest().getDiscountInPercent() > 0) {
            discountInEuro = discountInEuro.add(
                    invoice.getTotalNetAmountBeforeDiscount()
                            .divide(BigDecimal.valueOf(invoice.getStay().getGuest()
                                    .getDiscountInPercent())).setScale(2, RoundingMode.CEILING));
        }

        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .withStayId(stayId)
                .withInvoiceNumber(invoice.getInvoiceNumber())
                .withInvoiceDate(invoice.getInvoiceDate())
                .withGuestFirstName(invoice.getStay().getGuest().getName().firstName())
                .withGuestLastName(invoice.getStay().getGuest().getName().lastName())
                .withStreetName(invoice.getStay().getGuest().getAddress().streetName())
                .withStreetNumber(invoice.getStay().getGuest().getAddress().streetNumber())
                .withZipCode(invoice.getStay().getGuest().getAddress().zipCode())
                .withCity(invoice.getStay().getGuest().getAddress().city())
                .withAmountOfAdults(invoice.getStay().getBooking().getAmountOfAdults())
                .withAmountOfChildren(invoice.getStay().getBooking().getAmountOfChildren())
                .withServices(services)
                .withRoomNames(roomNames)
                .withCheckInDate(invoice.getStay().getCheckInDate())
                .withCheckOutDate(invoice.getStay().getCheckOutDate())
                .withAmountOfNights(invoice.getAmountOfNights())
                .withLocalTaxPerPerson(invoice.getLocalTaxPerPerson())
                .withLocalTaxTotal(invoice.getLocalTaxTotal())
                .withValueAddedTaxInPercent(invoice.getValueAddedTaxInPercent().setScale(1, RoundingMode.CEILING))
                .withValueAddedTaxInEuro(invoice.getValueAddedTaxInEuro())
                .withTotalNetAmountBeforeDiscount(invoice.getTotalNetAmountBeforeDiscount())
                .withTotalNetAmountAfterDiscount(invoice.getTotalNetAmountAfterDiscount())
                .withTotalNetAmountAfterLocalTax(invoice.getTotalNetAmountAfterLocalTax())
                .withTotalGrossAmount(invoice.getTotalGrossAmount())
                .withDiscountInPercent(invoice.getStay().getGuest().getDiscountInPercent())
                .withDiscountInEuro(discountInEuro)
                .withCategoryNames(categoryNames)
                .withCategoryPrices(categoryPrices)
                .build();

        return invoiceDTO;
    }

    // TODO: Make this method private because it is only used in this class
    /**
     * This class saves the invoice into the database.
     * @param stayId contains the id of the stay.
     * @param roomNames contains the names of the rooms to be paid.
     * @param action contains the information so either the stay will be checked out or an invoice will be created.
     * @throws StayNotFoundException if the stay could not be found.
     * @throws RoomNotFoundException if the room could not be found.
     * @throws SeasonNotFoundException if the season could not be found.
     */
    @Transactional
    @Override
    public void saveInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Saving invoice failed! Stay with id " + stayId + " not found")
        );

        for (Map.Entry<Room, Boolean> room : stay.getRooms().entrySet()) {
            for (String roomName : roomNames) {
                if (room.getKey().getName().name().equals(roomName)) {
                    room.setValue(true);
                }
            }
        }

        Invoice invoice = invoiceCreationService.createInvoice(stay, roomNames, action);

        invoiceRepository.add(invoice);
    }

    /**
     * This method deactivates a stay and performs a check out.
     * @param stayId contains the id of the stay.
     * @param roomNames contains the names of the rooms to be paid.
     * @param action contains the information so either the stay will be checked out or an invoice will be created.
     * @throws StayNotFoundException if the stay could not be found.
     * @throws RoomNotFoundException if the room could not be found.
     * @throws SeasonNotFoundException if the season could not be found.
     */
    @Transactional
    @Override
    public void checkOut(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Check out failed! Stay with id " + stayId + " doesn't exist.")
        );

        Invoice invoice = invoiceCreationService.createInvoice(stay, roomNames, action);

        invoiceRepository.add(invoice);

        for (Map.Entry<Room, Boolean> room : stay.getRooms().entrySet()) {
            room.getKey().clean();
        }

        stay.deactivate();
    }
}
