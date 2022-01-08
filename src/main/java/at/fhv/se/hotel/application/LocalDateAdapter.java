package at.fhv.se.hotel.application;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Generated
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v);
    }

    @Generated
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
