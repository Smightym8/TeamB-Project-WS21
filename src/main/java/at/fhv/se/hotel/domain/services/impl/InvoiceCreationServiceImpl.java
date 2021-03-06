package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCreationService;
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

/**
 * This class represents the implementation of the interface {@link InvoiceCreationService}.
 * It provides the functionality to create an invoice.
 */
@Component
public class InvoiceCreationServiceImpl implements InvoiceCreationService {

    private static final BigDecimal localTaxInEuro = new BigDecimal("0.76");
    private static final BigDecimal valueAddedTaxPercentage = new BigDecimal("0.10");

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private RoomRepository roomRepository;

    /**
     * This method takes a stay, room names and an action to create an invoice or to directly check out the stay.
     * @param stay contains the stay for the invoice
     * @param roomNames contains the room names which will be billed
     * @param action contains the action and determines whether only an invoice is created or the stay is also checked out
     * @return the created invoice
     * @throws SeasonNotFoundException if there is no season found to get the prices for the room category
     * @throws RoomNotFoundException if a room can't be found
     */
    @Override
    public Invoice createInvoice(Stay stay, List<String> roomNames, String action) throws SeasonNotFoundException, RoomNotFoundException {
        int todaysInvoicesAmount = invoiceRepository.amountOfInvoicesByDate(LocalDate.now()) + 1;
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

        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < nights; i++) {
            LocalDate finalTempDate = tempDate;
            Season currentSeason = seasonRepository.seasonByDate(tempDate).orElseThrow(
                    () -> new SeasonNotFoundException("Season for date " + finalTempDate + " not found.")
            );


            for (String name : roomNames) {
                Room room = roomRepository.roomByName(new RoomName(name)).orElseThrow(
                        () -> new RoomNotFoundException("Room " + name + " not found.")
                );

                if (!rooms.contains(room)) {
                    rooms.add(room);
                }

                RoomCategoryPrice currentCategoryPrice = roomCategoryPriceService.by(
                        room.getRoomCategory(), currentSeason.getSeasonId()
                );

                totalNetAmountBeforeDiscount = totalNetAmountBeforeDiscount.add((currentCategoryPrice.getPrice()));

                if (!roomCategoryPriceList.contains(currentCategoryPrice)) {
                    roomCategoryPriceList.add(currentCategoryPrice);
                }
            }

            tempDate = tempDate.plusDays(1);
        }


        // Calculate discount
        BigDecimal discountInPercent = BigDecimal.valueOf(stay.getGuest().getDiscountInPercent());
        BigDecimal discountInEuro = discountInPercent.compareTo(new BigDecimal("0")) == 0
                ? new BigDecimal("0")
                : totalNetAmountBeforeDiscount.multiply(discountInPercent.divide(new BigDecimal("100"), RoundingMode.CEILING));
        BigDecimal totalNetAmountAfterDiscount = totalNetAmountBeforeDiscount.subtract(discountInEuro);

        // Calculate vat
        BigDecimal valueAddedTaxTotal = totalNetAmountAfterDiscount.multiply(valueAddedTaxPercentage);

        // Calculate local tax
        BigDecimal localTaxTotal = new BigDecimal("0");

        if (action.equals("checkOut")) {
            localTaxTotal = localTaxTotal.add(localTaxInEuro.multiply
                    (BigDecimal.valueOf(stay.getBooking().getAmountOfAdults())));
        }

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
                rooms,
                nights,
                localTaxInEuro,
                localTaxTotal.setScale(2, RoundingMode.CEILING),
                valueAddedTaxPercentage,
                valueAddedTaxTotal.setScale(2, RoundingMode.CEILING),
                totalNetAmountBeforeDiscount.setScale(2, RoundingMode.CEILING),
                discountInPercent,
                discountInEuro.setScale(2, RoundingMode.CEILING),
                totalNetAmountAfterDiscount.setScale(2, RoundingMode.CEILING),
                totalNetAmountAfterLocalTax.setScale(2, RoundingMode.CEILING),
                totalGrossAmount.setScale(2, RoundingMode.CEILING)
        );
    }
}