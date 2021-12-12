package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXB;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class InvoicePDFRepositoryImpl implements InvoicePDFRepository {
    private static final String INVOICES_PDF_PATH = "src/main/resources/static/invoices/pdf/";
    private static final String INVOICES_XML_PATH = "src/main/resources/static/invoices/xml/";
    private static final String FILE_NAME_PREFIX = "invoice_";

    @Override
    public Optional<ByteArrayResource> findInvoiceByNo(String invoiceNo) {
        String fileName = FILE_NAME_PREFIX + invoiceNo + ".pdf";

        Path path = Paths.get(INVOICES_PDF_PATH + fileName);

        byte[] data;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }


        return Optional.of(new ByteArrayResource(data));
    }

    @Override
    public void saveAsXml(InvoiceDTO invoiceDTO) {
        String fileName = FILE_NAME_PREFIX + invoiceDTO.invoiceNumber() + ".xml";

        try {
            OutputStream os = new FileOutputStream(INVOICES_XML_PATH + fileName);
            JAXB.marshal(invoiceDTO, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
