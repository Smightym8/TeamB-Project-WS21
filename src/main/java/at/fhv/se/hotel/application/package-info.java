@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type= LocalDate.class,
                value=LocalDateAdapter.class),
})
package at.fhv.se.hotel.application;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;