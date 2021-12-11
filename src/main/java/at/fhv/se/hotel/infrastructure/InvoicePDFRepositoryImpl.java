package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class InvoicePDFRepositoryImpl implements InvoicePDFRepository {
    private static final String INVOICES_PATH = "src/main/resources/static/invoices/";

    @Override
    public Optional<ByteArrayResource> findInvoiceByNo(String invoiceNo) {
        String fileName = "invoice_" + invoiceNo + ".pdf";

        Path path = Paths.get(INVOICES_PATH + fileName);

        byte[] data;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }


        return Optional.of(new ByteArrayResource(data));
    }
}
