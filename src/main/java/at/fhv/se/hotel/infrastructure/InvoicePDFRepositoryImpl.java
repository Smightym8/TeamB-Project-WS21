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

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class InvoicePDFRepositoryImpl implements InvoicePDFRepository {
    private static final String INVOICE_PDF_TEMPLATE = "src/main/resources/static/invoices/xslt/invoice2pdf.xsl";
    private static final String INVOICES_PDF_PATH = "src/main/resources/static/invoices/pdf/";
    private static final String INVOICES_XML_PATH = "src/main/resources/static/invoices/xml/";
    private static final String FILE_NAME_PREFIX = "invoice_";
    private static final String PDF_SUFFIX = ".pdf";
    private static final String XML_SUFFIX = ".xml";

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
    public void saveAsPDF(InvoiceDTO invoiceDTO) {
        String fileName = FILE_NAME_PREFIX + invoiceDTO.invoiceNumber();

        try {
            OutputStream os = new FileOutputStream(INVOICES_XML_PATH + fileName + XML_SUFFIX);
            JAXB.marshal(invoiceDTO, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Convert xml to pdf
        try {
            convertToPDF(fileName);
        } catch (IOException | FOPException | TransformerException e) {
            e.printStackTrace();
        }

        // Delete xml file
        File xmlFile = new File(INVOICES_XML_PATH + fileName + XML_SUFFIX);
        xmlFile.delete();
    }

    /**
     * Method that will convert the given XML to PDF
     * @throws IOException
     * @throws FOPException
     * @throws TransformerException
     */
    private void convertToPDF(String fileName)  throws IOException, FOPException, TransformerException {
        // the XSL FO file
        File xsltFile = new File(INVOICE_PDF_TEMPLATE);
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File(INVOICES_XML_PATH + fileName + XML_SUFFIX));
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;
        out = new java.io.FileOutputStream(INVOICES_PDF_PATH + fileName + PDF_SUFFIX);

        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }
}
