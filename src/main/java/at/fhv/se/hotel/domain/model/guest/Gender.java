package at.fhv.se.hotel.domain.model.guest;

/**
 * This class is an enum which contains the available genders
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    DIVERSE("Diverse");

    private final String friendlyName;

    Gender(String name) {
        this.friendlyName = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
