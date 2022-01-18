package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.impl.InvoiceDownloadServiceImpl;
import org.apache.fop.apps.FOPException;
import org.springframework.core.io.ByteArrayResource;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * This class represents an interface that defines the functionality to download an invoice.
 * The implementation is in {@link InvoiceDownloadServiceImpl}
 */
public interface InvoiceDownloadService {
    /**
     * See implementation {@link InvoiceDownloadServiceImpl#download(String)}
     */
    ByteArrayResource download(String invoiceNumber) throws InvoiceNotFoundException, JAXBException, FOPException, IOException, TransformerException;
}
