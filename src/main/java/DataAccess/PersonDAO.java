package DataAccess;

import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Person database access object class to perform all operations pertaining to persons in the database.
 */
public class PersonDAO {

    private final Connection conn;

    /**
     * Constructor that takes a database connection as a parameter.
     *
     * @param conn the database connection
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a person object into the person table in the database.
     *
     * @param person the person object to be inserted
     * @throws DataAccessException if an error occurs while inserting the person
     */
    public void insert(Person person) throws DataAccessException {

        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * Finds a person in the database and returns a person object.
     *
     * @param personID the id used to find the person in the database
     * @return the person object
     * @throws DataAccessException if an error occurs while searching for the person
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Clears the database of all persons.
     *
     * @throws DataAccessException if an error occurs while deleting the person table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Persons table");
        }
    }

    /**
     * Deletes a user in the database by personID.
     *
     * @param personID the personID
     * @throws DataAccessException if an error occurs
     */
    public void deletePerson(String personID) throws DataAccessException {
        if (find(personID) == null) {
            throw new DataAccessException("Could not delete person who doesn't exist in Persons table.");
        }
        String sql = "DELETE FROM Persons WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person from the Persons table.");
        }
    }

    /**
     * Grabs all person objects for a particular user's family tree and
     * returns them as a list.
     *
     * @param username username used to find user in database
     * @return A list of events from the users family tree
     */
    public List<Person> findAllPersonsForUser(String username) throws DataAccessException {
        List<Person> listOfPersons = new ArrayList<>();
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));

                listOfPersons.add(person);
            }

            if (listOfPersons.isEmpty()) {
                throw new DataAccessException("There were no Persons in the database associated with the user: " + username);
            }

            return listOfPersons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a list of persons in the database");
        }
    }
}
