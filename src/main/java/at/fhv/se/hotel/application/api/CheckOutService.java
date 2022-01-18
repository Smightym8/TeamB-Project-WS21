package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.impl.CheckOutServiceImpl;

import java.util.List;

/**
 * This class respresents an interface that defines
 * the check out of a stay
 * the creation of an invoice
 * the saving of an invoice into the database.
 * The implementation is in {@link CheckOutServiceImpl}
 */
public interface CheckOutService {

    /**
     * See implementation {@link CheckOutService#checkOut(String, List, String)}
     */
    void checkOut(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;

    /**
     * See implementation {@link CheckOutService#createInvoice(String, List, String)}
     */
    InvoiceDTO createInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;

    /**
     * See implementation {@link CheckOutService#saveInvoice(String, List, String)}
     */
    void saveInvoice(String stayId, List<String> roomNames, String action) throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException;
}
