package DataAccess;

import Model.Event;
import Model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Event database access object class to perform all operations pertaining to events in the database.
 */
public class EventDAO {

    private final Connection conn;

    /**
     * Constructor that takes a database connection as a parameter.
     *
     * @param conn the database connection
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an Event object into the event table in the database.
     *
     * @param event the event object to be inserted
     * @throws DataAccessException if an error occurs while inserting the event
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event in the database and returns an event object.
     *
     * @param eventID the id used to find the event in the database
     * @return the event object
     * @throws DataAccessException if an error occurs while searching for the event
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Clears the database of all events.
     *
     * @throws DataAccessException if an error occurs while deleting the event table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    /**
     * Grabs all event objects for a particular user's family tree and
     * returns them as a list.
     *
     * @param username username used to find user in database
     * @return A list of events from the users family tree
     */
    public List<Event> findAllEventsForUser(String username) throws DataAccessException {
        List<Event> listOfEvents = new ArrayList<>();
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));

                listOfEvents.add(event);
            }

            if (listOfEvents.isEmpty()) {
                throw new DataAccessException("There were no events in the database associated with the user: " + username);
            }

            return listOfEvents;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a list of events in the database");
        }
    }

    /**
     * Deletes an event in the database by eventID.
     *
     * @param eventID the eventID
     * @throws DataAccessException if an error occurs
     */
    public void deleteEvent(String eventID) throws DataAccessException {
        if (find(eventID) == null) {
            throw new DataAccessException("Could not delete event who doesn't exist in Events table.");
        }
        String sql = "DELETE FROM Events WHERE eventID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an event from the Events table.");
        }
    }

    /**
     * Grabs all event objects for a particular person family tree and
     * returns them as a list.
     *
     * @param personID used to find events in database
     * @return A list of events from the person
     */
    public List<Event> findAllEventsForPerson(String personID) throws DataAccessException {
        List<Event> listOfEvents = new ArrayList<>();
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("AssociatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));

                listOfEvents.add(event);
            }

            if (listOfEvents.isEmpty()) {
                throw new DataAccessException("There were no events in the database associated with the personID: " + personID);
            }

            return listOfEvents;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a list of events in the database");
        }
    }
}
