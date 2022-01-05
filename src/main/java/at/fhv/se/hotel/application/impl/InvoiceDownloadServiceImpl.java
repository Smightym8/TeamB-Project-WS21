package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.InvoiceDownloadService;
import at.fhv.se.hotel.application.api.InvoiceListingService;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDownloadServiceImpl implements InvoiceDownloadService {
    @Autowired
    InvoicePDFRepository invoicePDFRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceListingService invoiceListingService;

    // TODO: michi InvoicePDFGenerationException
    @Override
    public ByteArrayResource download(String invoiceId) throws InvoiceNotFoundException {

        InvoiceDTO invoiceDTO = invoiceListingService.findInvoiceById(invoiceId);

        return invoicePDFRepository.generatePDF(invoiceDTO).orElseThrow(
                () -> new InvoiceNotFoundException("PDF generation for Invoice with id " + invoiceId + " failed.")
        );
    }
}
