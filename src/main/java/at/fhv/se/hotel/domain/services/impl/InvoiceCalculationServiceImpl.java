package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCalculationService;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class InvoiceCalculationServiceImpl implements InvoiceCalculationService {

    private static final BigDecimal localTaxInEuro = new BigDecimal("0.76");
    private static final BigDecimal valueAddedTaxPercentage = new BigDecimal("0.1");

    @Autowired
    RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice calculateInvoice(Stay stay) {

        int todaysInvoicesAmount = invoiceRepository.invoicesByDate(LocalDate.now()).size();

        String invoiceSuffix = "";

        if (todaysInvoicesAmount < 10) {
            invoiceSuffix = "00" + String.valueOf(todaysInvoicesAmount);
        } else if (todaysInvoicesAmount < 100) {
            invoiceSuffix = "0" + String.valueOf(todaysInvoicesAmount);
        } else {
            invoiceSuffix = String.valueOf(todaysInvoicesAmount);
        }

        String invoiceNumber = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + invoiceSuffix;

        BigDecimal totalNetAmount = new BigDecimal("0");

        // Calculate Services
        for (Service s : stay.getServices()) {
            totalNetAmount = totalNetAmount.add(s.getServicePrice().price());
        }

        // Calculate RoomCategoryPrices
        int nights = Period.between(stay.getCheckInDate(), stay.getCheckOutDate()).getDays();
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(BookingWithRoomCategory brc : stay.getBooking().getRoomCategories()) {
                totalNetAmount = totalNetAmount.add(
                        (
                                roomCategoryPriceService.by(
                                        brc.getRoomCategory(), currentSeason
                                ).getPrice()
                        ).multiply(new BigDecimal(brc.getAmount()))
                );
            }

            tempDate = tempDate.plusDays(1);
        }

        // Calculate local tax
        BigDecimal localTaxTotal = localTaxInEuro.multiply(
                BigDecimal.valueOf(
                        stay.getBooking().getAmountOfAdults()
                )
        );

        // Calculate vat
        BigDecimal valueAddedTaxTotal = totalNetAmount.multiply(valueAddedTaxPercentage);

        // Calculate total net amount
        totalNetAmount = totalNetAmount.add(localTaxTotal);

        // Calculate total gross amount
        BigDecimal totalGrossAmount = totalNetAmount.add(valueAddedTaxTotal);

        return Invoice.create(
                invoiceRepository.nextIdentity(),
                invoiceNumber,
                stay,
                nights,
                localTaxInEuro,
                localTaxTotal,
                valueAddedTaxPercentage,
                valueAddedTaxTotal,
                totalNetAmount,
                totalGrossAmount
        );
    }
}
