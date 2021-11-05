package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.model.booking.Booking;

import java.util.List;

public class Service {
    // Required by hibernate
    private String id;
    private ServiceId serviceId;
    private ServiceName serviceName;
    private Price servicePrice;
    private List<Booking> bookings;

    public static Service create (ServiceId aServiceId, ServiceName aServiceName,
                                  Price aServicePrice, List<Booking> aBookings) {
        return new Service(aServiceId, aServiceName, aServicePrice, aBookings);
    }

    private Service (ServiceId aServiceId, ServiceName aServiceName,
                    Price aServicePrice, List<Booking> aBookings){
        this.serviceId = aServiceId;
        this.serviceName = aServiceName;
        this.servicePrice = aServicePrice;
        this.bookings = aBookings;
    }

    public ServiceId getServiceId() {
        return serviceId;
    }

    public void setServiceId(ServiceId serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public Price getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Price servicePrice) {
        this.servicePrice = servicePrice;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
