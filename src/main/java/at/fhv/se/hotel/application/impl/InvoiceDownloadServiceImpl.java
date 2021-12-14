package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.InvoiceDownloadService;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDownloadServiceImpl implements InvoiceDownloadService {
    @Autowired
    InvoicePDFRepository invoicePDFRepository;

    @Override
    public ByteArrayResource download(String invoiceNo) throws InvoiceNotFoundException {
        return invoicePDFRepository.findInvoiceByNo(invoiceNo).orElseThrow(
                () -> new InvoiceNotFoundException("Invoice with no. " + invoiceNo + " not found")
        );
    }
}
