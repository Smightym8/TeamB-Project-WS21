package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceCalculationServiceImpl implements InvoiceCalculationService {

    private static final BigDecimal localTaxInEuro = new BigDecimal("0.76");
    private static final BigDecimal valueAddedTaxPercentage = new BigDecimal("0.1");
    // TODO: vat 10 anstatt 0.1, Zahlen generell auf 2 Nachkommastellen anpassen

    @Autowired
    RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice calculateInvoice(Stay stay) {
        int todaysInvoicesAmount = invoiceRepository.invoicesByDate(LocalDate.now()).size() + 1;
        List<RoomCategoryPrice> roomCategoryPriceList = new ArrayList<>();

        String invoiceSuffix = "";

        if (todaysInvoicesAmount < 10) {
            invoiceSuffix = "00" + todaysInvoicesAmount;
        } else if (todaysInvoicesAmount < 100) {
            invoiceSuffix = "0" + todaysInvoicesAmount;
        } else {
            invoiceSuffix = String.valueOf(todaysInvoicesAmount);
        }

        String invoiceNumber = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + invoiceSuffix;

        BigDecimal totalNetAmount = new BigDecimal("0");

        // Calculate Services
        List<Service> services = new ArrayList<>();
        for (Service s : stay.getServices()) {
            totalNetAmount = totalNetAmount.add(s.getServicePrice().price());
            services.add(s);
        }

        // Calculate RoomCategoryPrices
        int nights = Period.between(stay.getCheckInDate(), stay.getCheckOutDate()).getDays();
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(BookingWithRoomCategory brc : stay.getBooking().getRoomCategories()) {
                RoomCategoryPrice currentCategoryPrice = roomCategoryPriceService.by(
                        brc.getRoomCategory(), currentSeason
                );

                totalNetAmount = totalNetAmount.add(
                        (
                                currentCategoryPrice.getPrice()
                        ).multiply(new BigDecimal(brc.getAmount()))
                );

                if(!roomCategoryPriceList.contains(currentCategoryPrice)) {
                    roomCategoryPriceList.add(currentCategoryPrice);
                }
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
                roomCategoryPriceList,
                services,
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
