package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
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
import java.util.Map;

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

    // TODO: Alle Tests wo List<Room> vorkommt anpassen -> Map<Room, Boolean>

     /*
        -> split invoice bekommt roomNames von stayDetailsView
        -> ähnlich wie createBookingService, dass ausgwählte in die Liste kommen
        -> split invoice, splitInvoice (Stay stay, List<String> roomNames)
        -> Liste roomNames enthält nur Räume die bezahlt werden sollen
        -> for each room in roomNames, getPrice -> add to calculation
        -> roomNames, for each über Map<Room, Boolean> rooms im Stay -> put.value(true)
     */

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

        BigDecimal totalNetAmount = new BigDecimal("0");

        String invoiceNumber = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + invoiceSuffix;

        // Calculate Services
        List<Service> services = new ArrayList<>();
        for (Service s : stay.getServices()) {
            totalNetAmount = totalNetAmount.add(s.getServicePrice().price().multiply(BigDecimal.valueOf(roomNames.size())));
            services.add(s);
        }

        // Calculate RoomCategoryPrices
        int nights = Period.between(stay.getCheckInDate(), stay.getCheckOutDate()).getDays();
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(String name : roomNames) {
                // TODO: Use RoomNotFoundException
                Room room = roomRepository.roomByName(name).get();

                RoomCategoryPrice currentCategoryPrice = roomCategoryPriceService.by(
                        room.getRoomCategory(), currentSeason
                );

                totalNetAmount = totalNetAmount.add((currentCategoryPrice.getPrice()));

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

        // Calculate discount
        double discount = 1.0 - (stay.getGuest().getDiscountInPercent() / 100.0); // 1.0 - (10.0 / 100.0)
        BigDecimal discountInvoice = BigDecimal.valueOf(discount);
        totalGrossAmount = totalGrossAmount.multiply(discountInvoice);

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
                totalNetAmount.setScale(2, RoundingMode.CEILING),
                totalGrossAmount.setScale(2, RoundingMode.CEILING)
        );
    }
}