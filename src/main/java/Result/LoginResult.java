package Result;

/**
 * A class to represent a login response body from the web API.
 */
public class LoginResult extends BaseResult {

    private String authtoken;
    private String username;
    private String personID;

    /**
     * A constructor for the LoginResult object.
     *
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */
    public LoginResult(String authtoken, String username, String personID, boolean success, String message) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public LoginResult(String message, boolean success) {
        super(message, success);
    }

    public String getAuthoken() {
        return authtoken;
    }

    public void setAuthoken(String authoken) {
        this.authtoken = authoken;
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
