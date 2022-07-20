package DataAccess;

import Model.Authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Authtoken database access object class to perform all operations pertaining to authtokens in the database.
 */
public class AuthtokenDAO {

    private final Connection conn;

    /**
     * Constructor that takes a database connection as a parameter.
     *
     * @param conn the database connection
     */
    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an authtoken object into the authtoken table in the database.
     *
     * @param authtoken the authtoken object to be inserted
     * @throws DataAccessException if an error occurs while inserting the authtoken
     */
    public void insert(Authtoken authtoken) throws DataAccessException {

        String sql = "INSERT INTO Authtokens (authtoken, username) VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * Finds an authtoken in the database and returns an authtoken object.
     *
     * @param authtoken the id used to find the authtoken in the database
     * @return the authtoken object
     * @throws DataAccessException if an error occurs while searching for the authtoken
     */
    public Authtoken find(String authtoken) throws DataAccessException {
        Authtoken token;
        ResultSet rs;
        String sql = "SELECT * FROM Authtokens WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    /**
     * Clears the databse of all events.
     *
     * @throws DataAccessException if an error occurs while deleting the authtoken table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtokens table");
        }
    }
}
