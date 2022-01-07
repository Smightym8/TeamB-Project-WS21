package at.fhv.se.hotel.domain.model.guest;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    DIVERS("Divers");

    private final String friendlyName;

    Gender(String name) {
        this.friendlyName = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
