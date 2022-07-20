package DataAccess;

import Model.Authtoken;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

/**
 * User database access object class to perform all operations pertaining to users in the database.
 */
public class UserDAO {

    private final Connection conn;

    /**
     * Constructor that takes a database connection as a parameter.
     *
     * @param conn the database connection
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a user object into the user table in the database.
     *
     * @param user the user object to be inserted
     * @throws DataAccessException if an error occurs while inserting the user
     */
    public void insert(User user) throws DataAccessException {

        String sql = "INSERT INTO Users (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Finds a user in the database and returns a user object.
     *
     * @param username the username used to find the user in the database
     * @return the user object
     * @throws DataAccessException if an error occurs while searching for the user
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Clears the database of all users.
     *
     * @throws DataAccessException if an error occurs while deleting the user table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Users table");
        }
    }

    /**
     * Validates the user's username and password.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if both username and password are correct, false if not
     */
    public boolean validate(String username, String password) {
        User user = null;
        try {
            user = find(username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        if (user == null) {
            return false;
        }
        return Objects.equals(user.getPassword(), password);
    }

    /**
     * Deletes all authtokens, events, and persons related to this username.
     *
     * @param username the username to clear
     */
    public void deleteDataForUser(String username) throws DataAccessException {

        if (find(username) == null) {
            throw new DataAccessException("Can't delete data for a user that doesn't exist in database.");
        }

        String sql = "DELETE FROM Authtokens WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an authtoken from the database");
        }

        String sql2 = "DELETE FROM Persons WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person from the database");
        }

        String sql3 = "DELETE FROM Events WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an event from the database");
        }
    }
}
