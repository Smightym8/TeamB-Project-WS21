package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckOutService;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.*;
import at.fhv.se.hotel.domain.services.api.InvoiceSplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckOutServiceImpl implements CheckOutService {

    @Autowired
    StayRepository stayRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceSplitService invoiceSplitService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    InvoicePDFRepository invoicePDFRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Transactional
    @Override
    public InvoiceDTO createInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Creating invoice failed! Stay with id " + stayId + " not found")
        );

        Invoice invoice = invoiceSplitService.splitInvoice(stay, roomNames, action);

        Map<String, BigDecimal> services = new HashMap<>();
        for(Service s : invoice.getServices()) {
            services.put(s.getServiceName().name(), s.getServicePrice().price());
        }

        List<String> categoryNames = new ArrayList<>();
        for (String name : roomNames) {
            // TODO: EXCEPTION!!!!!!
            categoryNames.add(roomRepository.roomByName(new RoomName(name)).get().getRoomCategory().getRoomCategoryName().name());
        }

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

        invoicePDFRepository.saveAsPDF(invoiceDTO);

        return invoiceDTO;

    }

    @Transactional
    @Override
    public void saveInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException {
        if (stayRepository.stayById(new StayId(stayId)).isPresent()) {
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
            Invoice invoice = invoiceSplitService.splitInvoice(stay, roomNames, action);

            invoiceRepository.add(invoice);
        }
    }

    @Transactional
    @Override
    public void checkOut(String stayId, List<String> roomNames, String action) throws StayNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Check out failed! Stay with id " + stayId + " doesn't exist.")
        );

        Invoice invoice = invoiceSplitService.splitInvoice(stay, roomNames, action);

        invoiceRepository.add(invoice);

        for (Map.Entry<Room, Boolean> room : stay.getRooms().entrySet()) {
            room.getKey().clean();
        }

        stay.deactivate();
    }
}
