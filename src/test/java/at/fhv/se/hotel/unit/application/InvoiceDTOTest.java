package at.fhv.se.hotel.unit.application;

import at.fhv.se.hotel.application.dto.InvoiceDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceDTOTest {

    @Test
    void given_invoicedtodetails_when_createinvoicedto_then_detailsequals() {
        // given
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
        List<BigDecimal> roomCategoryPricesExpected = List.of(
                new BigDecimal("400"),
                new BigDecimal("500")
        );
        LocalDate checkInDateExpected = LocalDate.of(2021,11,20);
        LocalDate checkOutDateExpected = LocalDate.of(2021,11,23);
        BigDecimal invoiceAmountExpected = new BigDecimal("1100");
        BigDecimal invoiceTaxesExpected = new BigDecimal("200");
        BigDecimal invoiceTotalAmountExpected = new BigDecimal("1300");
        LocalDate dueByExpected = checkOutDateExpected.plusDays(14);

        // when
        InvoiceDTO invoiceDTOExpected = InvoiceDTO.builder()
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
                .withInvoiceAmount(invoiceAmountExpected)
                .withInvoiceTaxes(invoiceTaxesExpected)
                .withTotalAmount(invoiceTotalAmountExpected)
                .withDueByDate(dueByExpected)
                .build();

        // then
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
        assertEquals(invoiceAmountExpected, invoiceDTOExpected.invoiceAmount());
        assertEquals(invoiceTaxesExpected, invoiceDTOExpected.invoiceTaxes());
        assertEquals(invoiceTotalAmountExpected, invoiceDTOExpected.invoiceTotalAmount());
        assertEquals(dueByExpected, invoiceDTOExpected.dueByDate());
    }
}
