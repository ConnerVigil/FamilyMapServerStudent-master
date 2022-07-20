package Model;

/**
 * An Authtoken class to hold a single row of data in the database.
 */
public class Authtoken {

    private String authtoken;
    private String username;

    /**
     * Constructor for authtoken object
     *
     * @param authtoken
     * @param username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Authtoken temp = (Authtoken)obj;
        if (!this.authtoken.equals(temp.getAuthtoken())) {return false;}
        if (!this.username.equals(temp.getUsername())) {return false;}
        return true;
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
}
