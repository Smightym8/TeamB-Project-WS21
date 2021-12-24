package at.fhv.se.hotel.unit.application;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceDTOTest {

    @Test
    void given_invoicedtodetails_when_createinvoicedto_then_detailsequals() {
        // given
        String stayIdExpected = "1";
        String invoiceNumberExpected = "20210112001";
        LocalDate invoiceDateExpected = LocalDate.of(2021, 12, 1);
        String guestFirstNameExpected = "John";
        String guestLastNameExpected = "Doe";
        String streetNameExpected = "Nowherestreet";
        String streetNumberExpected = "42";
        String zipCodeExpected = "1337";
        String cityExpected = "Nocity";
        Map<String, BigDecimal> servicesExpected = Map.of(
                "TV", new BigDecimal("100"),
                "Breakfast", new BigDecimal("100")
        );
        Map<String, Integer> roomCategoriesExpected = Map.of(
                "Single Room", 1,
                "Double Room", 2
        );

        Map<String, BigDecimal> roomCategoryPricesExpected = Map.of(
               "Single Room", new BigDecimal("400"),
                "Double Room", new BigDecimal("500")
        );
        LocalDate checkInDateExpected = LocalDate.of(2021,11,20);
        LocalDate checkOutDateExpected = LocalDate.of(2021,11,23);
        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("2900");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("2900");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("2901.52");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("290");
        BigDecimal totalGrossAmountExpected = new BigDecimal("3191.52");

        // when
        InvoiceDTO invoiceDTOExpected = InvoiceDTO.builder()
                .withStayId(stayIdExpected)
                .withInvoiceNumber(invoiceNumberExpected)
                .withInvoiceDate(invoiceDateExpected)
                .withGuestFirstName(guestFirstNameExpected)
                .withGuestLastName(guestLastNameExpected)
                .withStreetName(streetNameExpected)
                .withStreetNumber(streetNumberExpected)
                .withZipCode(zipCodeExpected)
                .withCity(cityExpected)
                .withServices(servicesExpected)
                .withCategories(roomCategoriesExpected)
                .withCategoryPrices(roomCategoryPricesExpected)
                .withCheckInDate(checkInDateExpected)
                .withCheckOutDate(checkOutDateExpected)
                .withAmountOfNights(amountOfNightsExpected)
                .withLocalTaxPerPerson(localTaxPerPersonExpected)
                .withLocalTaxTotal(localTaxTotalExpected)
                .withValueAddedTaxInPercent(valueAddedTaxInPercentExpected)
                .withValueAddedTaxInEuro(valueAddedTaxInEuroExpected)
                .withTotalNetAmountBeforeDiscount(totalNetAmountBeforeDiscountExpected)
                .withTotalNetAmountAfterDiscount(totalNetAmountAfterDiscountExpected)
                .withTotalNetAmountAfterLocalTax(totalNetAmountAfterLocalTaxExpected)
                .withTotalGrossAmount(totalGrossAmountExpected)
                .build();

        // then
        assertEquals(stayIdExpected, invoiceDTOExpected.stayId());
        assertEquals(invoiceNumberExpected, invoiceDTOExpected.invoiceNumber());
        assertEquals(invoiceDateExpected, invoiceDTOExpected.invoiceDate());
        assertEquals(guestFirstNameExpected, invoiceDTOExpected.guestFirstName());
        assertEquals(guestLastNameExpected, invoiceDTOExpected.guestLastName());
        assertEquals(streetNameExpected, invoiceDTOExpected.streetName());
        assertEquals(streetNumberExpected, invoiceDTOExpected.streetNumber());
        assertEquals(zipCodeExpected, invoiceDTOExpected.zipCode());
        assertEquals(cityExpected, invoiceDTOExpected.city());
        assertEquals(servicesExpected.size(), invoiceDTOExpected.services().size());
        assertEquals(roomCategoriesExpected.size(), invoiceDTOExpected.roomCategories().size());
        assertEquals(roomCategoryPricesExpected.size(), invoiceDTOExpected.roomCategoryPrices().size());
        assertEquals(checkInDateExpected, invoiceDTOExpected.checkInDate());
        assertEquals(checkOutDateExpected, invoiceDTOExpected.checkOutDate());
        assertEquals(amountOfNightsExpected, invoiceDTOExpected.amountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceDTOExpected.localTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceDTOExpected.localTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceDTOExpected.valueAddedTaxInPercent());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoiceDTOExpected.totalNetAmountBeforeDiscount());
        assertEquals(totalNetAmountAfterDiscountExpected, invoiceDTOExpected.totalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoiceDTOExpected.totalNetAmountAfterLocalTax()  );
        assertEquals(valueAddedTaxInEuroExpected, invoiceDTOExpected.valueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoiceDTOExpected.totalGrossAmount());
    }
}
