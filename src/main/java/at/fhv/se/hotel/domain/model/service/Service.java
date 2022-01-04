package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

// TODO: Test
public class Service {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private ServiceId serviceId;
    private ServiceName serviceName;
    private Price servicePrice;

    // Required by hibernate
    @SuppressWarnings("unused")
    protected Service() {
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

    public ServiceName getServiceName() {
        return serviceName;
    }

    public Price getServicePrice() {
        return servicePrice;
    }

    @Generated
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

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, serviceId, serviceName, servicePrice);
    }
}
