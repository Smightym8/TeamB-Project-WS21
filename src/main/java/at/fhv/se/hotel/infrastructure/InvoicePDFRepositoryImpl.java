package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Optional;

@Component
public class InvoicePDFRepositoryImpl implements InvoicePDFRepository {
    private static final String INVOICE_PDF_TEMPLATE = "src/main/resources/static/invoices/xslt/invoice2pdf.xsl";

    @Override
    public Optional<ByteArrayResource> generatePDF(InvoiceDTO invoiceDTO) {

        byte[] invoicePDF;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JAXBContext context = JAXBContext.newInstance(invoiceDTO.getClass());
            Marshaller marshaller = context.createMarshaller();

            // create an instance of fop factory
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            // a user agent is needed for transformation
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            ByteArrayOutputStream xmlOutput = new ByteArrayOutputStream();
            marshaller.marshal(invoiceDTO, xmlOutput);
            byte[] o = xmlOutput.toByteArray();
            xmlOutput.close();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(o);

            Source source = new StreamSource(inputStream);
            Result result = new SAXResult(fop.getDefaultHandler());

            // Setup XSLT
            File xsltFile = new File(INVOICE_PDF_TEMPLATE);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            transformer.transform(source, result);

            invoicePDF = out.toByteArray();

        } catch (JAXBException | IOException | FOPException | TransformerException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(new ByteArrayResource(invoicePDF));
    }
}
