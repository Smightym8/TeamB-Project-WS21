package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.InvoiceListingService;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceListingServiceImpl implements InvoiceListingService {
    @Autowired
    InvoiceRepository invoiceRepository;

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
                    .build();

            invoiceListingDTOs.add(invoiceListingDTO);
        }

        return invoiceListingDTOs;
    }
}
