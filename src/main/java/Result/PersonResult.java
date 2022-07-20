package Result;

import Model.Person;

/**
 * A class to represent the person response body from the web API.
 */
public class PersonResult extends BaseResult {

    private Person[] data;

    /**
     * A constructor for the PersonResult object.
     *
     * @param data
     * @param success
     * @param message
     */
    public PersonResult(Person[] data, boolean success, String message) {
        super(message, success);
        this.data = data;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
