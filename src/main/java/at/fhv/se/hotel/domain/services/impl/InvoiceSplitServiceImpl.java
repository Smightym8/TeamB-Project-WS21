package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceSplitService;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceSplitServiceImpl implements InvoiceSplitService {

    private static final BigDecimal localTaxInEuro = new BigDecimal("0.76");
    private static final BigDecimal valueAddedTaxPercentage = new BigDecimal("0.10");

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public Invoice splitInvoice(Stay stay, List<String> roomNames) {

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
                            .multiply(BigDecimal.valueOf(roomNames.size()))
                            .multiply(BigDecimal.valueOf(nights)));
            services.add(s);
        }

        // Calculate RoomCategoryPrices
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(String name : roomNames) {
                // TODO: Use RoomNotFoundException
                Room room = roomRepository.roomByName(name).get();

                RoomCategoryPrice currentCategoryPrice = roomCategoryPriceService.by(
                        room.getRoomCategory(), currentSeason
                );

                totalNetAmountBeforeDiscount = totalNetAmountBeforeDiscount.add((currentCategoryPrice.getPrice()));

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

        return Invoice.create(
                invoiceRepository.nextIdentity(),
                invoiceNumber,
                stay,
                roomCategoryPriceList,
                services,
                nights,
                localTaxInEuro,
                localTaxTotal.setScale(2, RoundingMode.CEILING),
                valueAddedTaxPercentage,
                valueAddedTaxTotal.setScale(2, RoundingMode.CEILING),
                totalNetAmountBeforeDiscount.setScale(2, RoundingMode.CEILING),
                totalNetAmountAfterDiscount.setScale(2, RoundingMode.CEILING),
                totalNetAmountAfterLocalTax.setScale(2, RoundingMode.CEILING),
                totalGrossAmount.setScale(2, RoundingMode.CEILING)
        );
    }
}