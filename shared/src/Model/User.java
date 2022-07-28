package Model;

/**
 * A user class to hold a single row of data in the database.
 */
public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Constructor for User object
     *
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User temp = (User) obj;

        if (!this.username.equals(temp.getUsername())) {return false;}
        if (!this.password.equals(temp.getPassword())) {return false;}
        if (!this.email.equals(temp.getEmail())) {return false;}
        if (!this.firstName.equals(temp.getFirstName())) {return false;}
        if (!this.lastName.equals(temp.getLastName())) {return false;}
        if (!this.gender.equals(temp.getGender())) {return false;}
        if (!this.personID.equals(temp.getPersonID())) {return false;}

        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
