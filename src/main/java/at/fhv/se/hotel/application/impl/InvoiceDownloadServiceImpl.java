package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.InvoiceDownloadService;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class InvoiceDownloadServiceImpl implements InvoiceDownloadService {
    private static final String INVOICE_PDF_TEMPLATE = "src/main/resources/static/invoice/xslt/invoice2pdf.xsl";

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public ByteArrayResource download(String invoiceNumber) throws InvoiceNotFoundException, JAXBException, FOPException, IOException, TransformerException {
        Invoice invoice = invoiceRepository.invoiceByNumber(invoiceNumber).orElseThrow(
                () -> new InvoiceNotFoundException("Invoice with number " + invoiceNumber + " not found")
        );

        Map<String, BigDecimal> services = new HashMap<>();
        invoice.getServices().forEach(service ->
                services.put(service.getServiceName().name(), service.getServicePrice().price())
        );

        List<String> categoryNames = new ArrayList<>();
        List<String> roomNames = new ArrayList<>();
        List<BigDecimal> categoryPrices = new ArrayList<>();
        invoice.getRooms().forEach(room -> {
                    categoryNames.add(room.getRoomCategory().getRoomCategoryName().name());
                    roomNames.add(room.getName().name());

                    invoice.getRoomCategoryPriceList().forEach(roomCategoryPrice -> {
                                if(roomCategoryPrice.getRoomCategory().getRoomCategoryName().name().equals(room.getRoomCategory().getRoomCategoryName().name())) {
                                    categoryPrices.add(roomCategoryPrice.getPrice());
                                }
                            }
                    );

                }
        );

        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .withInvoiceNumber(invoice.getInvoiceNumber())
                .withInvoiceId(invoice.getInvoiceId().id())
                .withInvoiceDate(invoice.getInvoiceDate())
                .withGuestFirstName(invoice.getStay().getGuest().getName().firstName())
                .withGuestLastName(invoice.getStay().getGuest().getName().lastName())
                .withStreetName(invoice.getStay().getGuest().getAddress().streetName())
                .withStreetNumber(invoice.getStay().getGuest().getAddress().streetNumber())
                .withZipCode(invoice.getStay().getGuest().getAddress().zipCode())
                .withCity(invoice.getStay().getGuest().getAddress().city())
                .withAmountOfAdults(invoice.getStay().getBooking().getAmountOfAdults())
                .withAmountOfChildren(invoice.getStay().getBooking().getAmountOfChildren())
                .withServices(services)
                .withRoomNames(roomNames)
                .withCheckInDate(invoice.getStay().getBooking().getCheckInDate())
                .withCheckOutDate(invoice.getStay().getBooking().getCheckOutDate())
                .withAmountOfNights(invoice.getAmountOfNights())
                .withLocalTaxPerPerson(invoice.getLocalTaxPerPerson())
                .withLocalTaxTotal(invoice.getLocalTaxTotal())
                .withValueAddedTaxInPercent(invoice.getValueAddedTaxInPercent())
                .withValueAddedTaxInEuro(invoice.getValueAddedTaxInEuro())
                .withTotalNetAmountBeforeDiscount(invoice.getTotalNetAmountBeforeDiscount())
                .withTotalNetAmountAfterDiscount(invoice.getTotalNetAmountAfterDiscount())
                .withTotalNetAmountAfterLocalTax(invoice.getTotalNetAmountAfterLocalTax())
                .withTotalGrossAmount(invoice.getTotalGrossAmount())
                .withDiscountInPercent(invoice.getDiscountInPercent().doubleValue())
                .withDiscountInEuro(invoice.getDiscountInEuro())
                .withCategoryNames(categoryNames)
                .withCategoryPrices(categoryPrices)
                .build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

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

        byte[] invoicePDF = out.toByteArray();

        return new ByteArrayResource(invoicePDF);
    }
}
