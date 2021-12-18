package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckOutService;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCalculationService;
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
    InvoiceCalculationService invoiceCalculationService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoicePDFRepository invoicePDFRepository;

    @Transactional(readOnly = true)
    @Override
    public InvoiceDTO createInvoice(String stayId) throws StayNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Stay with id " + stayId + " not found")
        );

        Invoice invoice = invoiceCalculationService.calculateInvoice(stay);

        Map<String, BigDecimal> services = new HashMap<>();
        for(Service s : invoice.getServices()) {
            services.put(s.getServiceName().name(), s.getServicePrice().price());
        }

        Map<String, Integer> roomCategories = new HashMap<>();
        for(BookingWithRoomCategory brc : invoice.getStay().getBooking().getRoomCategories()) {
            roomCategories.put(brc.getRoomCategory().getRoomCategoryName().name(), brc.getAmount());
        }

        Map<String, BigDecimal> roomCategoryPrices = new HashMap<>();
        for(RoomCategoryPrice rcp : invoice.getRoomCategoryPriceList()) {
            roomCategoryPrices.put(rcp.getRoomCategory().getRoomCategoryName().name(), rcp.getPrice());
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
                .withCategories(roomCategories)
                .withCategoryPrices(roomCategoryPrices)
                .withCheckInDate(invoice.getStay().getCheckInDate())
                .withCheckOutDate(invoice.getStay().getCheckOutDate())
                .withAmountOfNights(invoice.getAmountOfNights())
                .withLocalTaxPerPerson(invoice.getLocalTaxPerPerson())
                .withLocalTaxTotal(invoice.getLocalTaxTotal())
                .withValueAddedTaxInPercent(invoice.getValueAddedTaxInPercent().setScale(1, RoundingMode.CEILING))
                .withValueAddedTaxInEuro(invoice.getValueAddedTaxInEuro())
                .withTotalNetAmount(invoice.getTotalNetAmount())
                .withTotalGrossAmount(invoice.getTotalGrossAmount())
                .withDiscountInPercent(invoice.getStay().getGuest().getDiscountInPercent())
                .withDiscountInEuro(invoice.getTotalNetAmount().add(invoice.getValueAddedTaxInEuro()).divide
                        (BigDecimal.valueOf(invoice.getStay().getGuest().getDiscountInPercent())).setScale(2, RoundingMode.CEILING))
                .build();

        invoicePDFRepository.saveAsPDF(invoiceDTO);

        return invoiceDTO;
    }

    @Transactional
    @Override
    public boolean checkOut(String stayId) {
        // TODO: Change return type to void and throw exception if stay is not found
        if (stayRepository.stayById(new StayId(stayId)).isPresent()) {

            Stay stay = stayRepository.stayById(new StayId(stayId)).get();
            Invoice invoice = invoiceCalculationService.calculateInvoice(stay);

            invoiceRepository.add(invoice);

            for(Room room : stay.getRooms()) {
                room.clean();
            }

            stay.deactivate();

            return true;
        } else {
            return false;
        }
    }
}
