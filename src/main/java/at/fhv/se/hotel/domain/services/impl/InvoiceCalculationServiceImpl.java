package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCalculationService;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: delete this and rename InvoiceSplitService and everything to do with split to InvoiceCalucationService and calculation

@Component
public class InvoiceCalculationServiceImpl implements InvoiceCalculationService {

    private static final BigDecimal localTaxInEuro = new BigDecimal("0.76");
    private static final BigDecimal valueAddedTaxPercentage = new BigDecimal("0.10");
    // TODO: vat 10 anstatt 0.1, Zahlen generell auf 2 Nachkommastellen anpassen

    @Autowired
    RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice calculateInvoice(Stay stay) {
        // TODO: Eigene Repo Methode, die Anzahl an Rechnungen zurückgibt
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

        BigDecimal totalNetAmountBeforeDiscount = new BigDecimal("0");

        // Calculate amount of nights
        int nights = Period.between(stay.getCheckInDate(), stay.getCheckOutDate()).getDays();

        // Calculate Services per room per night
        List<Service> services = new ArrayList<>();
        for (Service s : stay.getServices()) {
            totalNetAmountBeforeDiscount = totalNetAmountBeforeDiscount
                    .add(s.getServicePrice().price()
                            .multiply(BigDecimal.valueOf(stay.getRooms().size()))
                            .multiply(BigDecimal.valueOf(nights)));
            services.add(s);
        }

        // Calculate RoomCategoryPrices
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            // TODO: Throw exception if season isn't present
            Season currentSeason = seasonRepository.seasonByDate(tempDate).get();

            for(BookingWithRoomCategory brc : stay.getBooking().getRoomCategories()) {
                RoomCategoryPrice currentCategoryPrice = roomCategoryPriceService.by(
                        brc.getRoomCategory(), currentSeason.getSeasonId()
                );

                totalNetAmountBeforeDiscount = totalNetAmountBeforeDiscount.add(
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

        // Calculate discount
        double discount = 1.0 - (stay.getGuest().getDiscountInPercent() / 100.0); // 1.0 - (10.0 / 100.0)
        BigDecimal discountInvoice = BigDecimal.valueOf(discount);
        BigDecimal totalNetAmountAfterDiscount = totalNetAmountBeforeDiscount.multiply(discountInvoice);

        // Calculate vat
        BigDecimal valueAddedTaxTotal = totalNetAmountAfterDiscount.multiply(valueAddedTaxPercentage);

        // Calculate local tax
        BigDecimal localTaxTotal = localTaxInEuro.multiply(
                BigDecimal.valueOf(
                        stay.getBooking().getAmountOfAdults()
                )
        );

        // Calculate total net amount
        BigDecimal totalNetAmountAfterLocalTax = totalNetAmountAfterDiscount.add(localTaxTotal);

        // Calculate total gross amount
        BigDecimal totalGrossAmount = totalNetAmountAfterLocalTax.add(valueAddedTaxTotal);

        List<Room> rooms = new ArrayList<>(stay.getRooms().keySet());

        return Invoice.create(
                invoiceRepository.nextIdentity(),
                invoiceNumber,
                stay,
                roomCategoryPriceList,
                services,
                rooms,
                nights,
                localTaxInEuro,
                localTaxTotal.setScale(2, RoundingMode.CEILING),
                valueAddedTaxPercentage,
                valueAddedTaxTotal.setScale(2, RoundingMode.CEILING),
                totalNetAmountBeforeDiscount.setScale(2, RoundingMode.CEILING),
                new BigDecimal("0"),
                new BigDecimal("0"),
                totalNetAmountAfterDiscount.setScale(2, RoundingMode.CEILING),
                totalNetAmountAfterLocalTax.setScale(2, RoundingMode.CEILING),
                totalGrossAmount.setScale(2, RoundingMode.CEILING)
        );
    }
}
