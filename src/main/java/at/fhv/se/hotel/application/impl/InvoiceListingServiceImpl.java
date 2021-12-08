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
            // TODO: After implementing DTO, use withXXX methods
            invoiceListingDTOs.add(new InvoiceListingDTO());
        }

        return invoiceListingDTOs;
    }
}
