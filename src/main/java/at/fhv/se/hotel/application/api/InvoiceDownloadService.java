package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import org.apache.fop.apps.FOPException;
import org.springframework.core.io.ByteArrayResource;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface InvoiceDownloadService {
    ByteArrayResource download(String invoiceNumber) throws InvoiceNotFoundException, JAXBException, FOPException, IOException, TransformerException;
}
