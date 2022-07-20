package Result;

/**
 * A class to represent the register response body from the web API.
 */
public class RegisterResult extends BaseResult {

    private String authtoken;
    private String username;
    private String personID;

    /**
     * A constructor for the RegisterResult object.
     *
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public RegisterResult(String message, boolean success) {
        super(message, success);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
