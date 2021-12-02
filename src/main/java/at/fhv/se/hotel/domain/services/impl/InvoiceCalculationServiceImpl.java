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

@Component
public class InvoiceCalculationServiceImpl implements InvoiceCalculationService {
    @Autowired
    RoomCategoryPriceService roomCategoryPriceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice calculateInvoice(Stay stay) {
        BigDecimal totalAmount = new BigDecimal("0");

        // Calculate Services
        for (Service s : stay.getServices()) {
            totalAmount = totalAmount.add(s.getServicePrice().price());
        }

        // Calculate RoomCategoryPrices
        int nights = Period.between(stay.getCheckInDate(), stay.getCheckOutDate()).getDays();
        LocalDate tempDate = stay.getCheckInDate();

        for(int i = 0; i < nights; i++) {
            Season currentSeason = Season.seasonByDate(tempDate);
            for(BookingWithRoomCategory brc : stay.getBooking().getRoomCategories()) {
                totalAmount = totalAmount.add(
                        (
                                roomCategoryPriceService.by(
                                        brc.getRoomCategory(), currentSeason
                                ).getPrice()
                        ).multiply(new BigDecimal(brc.getAmount()))
                );
            }

            tempDate = tempDate.plusDays(1);
        }

        return Invoice.create(invoiceRepository.nextIdentity(), stay, nights, totalAmount);
    }
}
