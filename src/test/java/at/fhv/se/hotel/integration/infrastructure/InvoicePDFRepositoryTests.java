package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
import at.fhv.se.hotel.domain.repository.InvoicePDFRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class InvoicePDFRepositoryTests {
    @Autowired
    InvoicePDFRepository invoicePDFRepository;

    @Test
    public void given_invoiceDTO_when_save_and_get_invoice_then_returnNonNullByteArrayRessource() {
        // given
        String invoiceNo = "20211220001";

        InvoiceDTO invoiceDTOExpected = InvoiceDTO.builder()
                .withStayId("1")
                .withInvoiceNumber("20211220001")
                .withInvoiceDate(LocalDate.of(2021, 12, 20))
                .withGuestFirstName("John")
                .withGuestLastName("Doe")
                .withStreetName("Street")
                .withStreetNumber("1")
                .withZipCode("6850")
                .withCity("Dornbirn")
                .withAmountOfAdults(2)
                .withAmountOfChildren(1)
                .withServices(Map.of("Breakfast", new BigDecimal("100")))
                .withCategories(Map.of("Single Room", 1))
                .withCategoryPrices(Map.of("Single Room", new BigDecimal("300")))
                .withCheckInDate(LocalDate.of(2021, 8, 1))
                .withCheckOutDate(LocalDate.of(2021, 8, 10))
                .withAmountOfNights(9)
                .withLocalTaxPerPerson(new BigDecimal("0.52"))
                .withLocalTaxTotal(new BigDecimal("0.52"))
                .withValueAddedTaxInPercent(new BigDecimal("0.1"))
                .withValueAddedTaxInEuro(new BigDecimal("0.52"))
                .withTotalNetAmountBeforeDiscount(new BigDecimal("0.52"))
                .withTotalGrossAmount(new BigDecimal("0.52"))
                .withDiscountInPercent(10)
                .withDiscountInEuro(new BigDecimal("0.52"))
                .withCategoryNames(List.of("Single Room"))
                .withCategoryPrices(List.of(new BigDecimal("0.52")))
                .withTotalNetAmountAfterDiscount(new BigDecimal("200"))
                .withTotalNetAmountAfterLocalTax(new BigDecimal("200"))
                .build();
        // when
        invoicePDFRepository.saveAsPDF(invoiceDTOExpected);
        ByteArrayResource byteArrayResource = invoicePDFRepository.findInvoiceByNo(invoiceNo).get();

        // then
        assertNotNull(byteArrayResource);
    }
}
