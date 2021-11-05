package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.model.booking.Booking;

import java.util.List;
import java.util.Objects;

public class Service {
    // Required by hibernate
    private Long id;
    private ServiceId serviceId;
    private ServiceName serviceName;
    private Price servicePrice;

    // Required by hibernate
    public Service() {
    }

    public static Service create (ServiceId aServiceId, ServiceName aServiceName,
                                  Price aServicePrice) {
        return new Service(aServiceId, aServiceName, aServicePrice);
    }

    private Service (ServiceId aServiceId, ServiceName aServiceName,
                    Price aServicePrice){
        this.serviceId = aServiceId;
        this.serviceName = aServiceName;
        this.servicePrice = aServicePrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id)
                && Objects.equals(serviceId, service.serviceId)
                && Objects.equals(serviceName, service.serviceName)
                && Objects.equals(servicePrice, service.servicePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceId, serviceName, servicePrice);
    }
}
