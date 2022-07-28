package Result;

/**
 * A class to represent the eventID response body from the web API.
 */
public class EventIDResult extends BaseResult {

    private String associatedUsername;
    private String eventID;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    /**
     * A constructor for the EventIDResult object.
     *
     * @param associatedUsername
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param success
     * @param message
     */
    public EventIDResult(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year, boolean success, String message) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public EventIDResult(String message, boolean success) {
        super(message, success);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
