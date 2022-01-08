package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.InvoiceListingService;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
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
public class InvoiceListingServiceImpl implements InvoiceListingService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    RoomRepository roomRepository;

    @Transactional(readOnly = true)
    @Override
    public List<InvoiceListingDTO> allInvoices() {
        List<Invoice> invoices = invoiceRepository.findAllInvoices();

        List<InvoiceListingDTO> invoiceListingDTOs = new ArrayList<>();
        for(Invoice i : invoices) {
            InvoiceListingDTO invoiceListingDTO = InvoiceListingDTO.builder()
                    .withId(i.getInvoiceId().id())
                    .withInvoiceNumber(i.getInvoiceNumber())
                    .withGuestFirstName(i.getStay().getGuest().getName().firstName())
                    .withGuestLastName(i.getStay().getGuest().getName().lastName())
                    .withStreetName(i.getStay().getGuest().getAddress().streetName())
                    .withStreetNumber(i.getStay().getGuest().getAddress().streetNumber())
                    .withZipCode(i.getStay().getGuest().getAddress().zipCode())
                    .withCity(i.getStay().getGuest().getAddress().city())
                    .withTotalGrossAmountAmount(i.getTotalGrossAmount().toString())
                    .withIsPaid(i.isPaid())
                    .build();

            invoiceListingDTOs.add(invoiceListingDTO);
        }

        return invoiceListingDTOs;
    }

    @Override
    public InvoiceDTO findInvoiceById(String id) throws InvoiceNotFoundException {
        Invoice invoice = invoiceRepository.invoiceById(new InvoiceId(id)).orElseThrow(
                () -> new InvoiceNotFoundException("Invoice with id " + id + " not found")
        );

        Map<String, BigDecimal> services = new HashMap<>();
        invoice.getServices().forEach(service ->
                services.put(service.getServiceName().name(), service.getServicePrice().price())
        );

        List<String> categoryNames = new ArrayList<>();
        List<String> roomNames = new ArrayList<>();
        List<BigDecimal> categoryPrices = new ArrayList<>();
        invoice.getRooms().forEach(room -> {
                categoryNames.add(room.getRoomCategory().getRoomCategoryName().name());
                roomNames.add(room.getName().name());

                invoice.getRoomCategoryPriceList().forEach(roomCategoryPrice -> {
                        if(roomCategoryPrice.getRoomCategory().getRoomCategoryName().name().equals(room.getRoomCategory().getRoomCategoryName().name())) {
                            categoryPrices.add(roomCategoryPrice.getPrice());
                        }
                    }
                );

            }
        );




        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .withInvoiceNumber(invoice.getInvoiceNumber())
                .withInvoiceId(id)
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
                .withCheckInDate(invoice.getStay().getBooking().getCheckInDate())
                .withCheckOutDate(invoice.getStay().getBooking().getCheckOutDate())
                .withAmountOfNights(invoice.getAmountOfNights())
                .withLocalTaxPerPerson(invoice.getLocalTaxPerPerson())
                .withLocalTaxTotal(invoice.getLocalTaxTotal())
                .withValueAddedTaxInPercent(invoice.getValueAddedTaxInPercent())
                .withValueAddedTaxInEuro(invoice.getValueAddedTaxInEuro())
                .withTotalNetAmountBeforeDiscount(invoice.getTotalNetAmountBeforeDiscount())
                .withTotalNetAmountAfterDiscount(invoice.getTotalNetAmountAfterDiscount())
                .withTotalNetAmountAfterLocalTax(invoice.getTotalNetAmountAfterLocalTax())
                .withTotalGrossAmount(invoice.getTotalGrossAmount())
                .withDiscountInPercent(invoice.getDiscountInPercent().doubleValue())
                .withDiscountInEuro(invoice.getDiscountInEuro())
                .withCategoryNames(categoryNames)
                .withCategoryPrices(categoryPrices)
                .build();

        return invoiceDTO;
    }
}
