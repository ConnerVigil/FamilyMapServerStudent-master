package Services;

import DataAccess.AuthtokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.Authtoken;
import Model.Event;
import Result.EventIDResult;
import Result.PersonIDResult;

import java.util.Objects;

/**
 * A class to perform the clear api web call.
 */
public class EventIDService {

    /**
     * Find an event by it's id service for the eventid api call.
     *
     * @param eventID of the object to find.
     * @param authtoken needed to perfrom service
     * @return EventIDResult object response.
     */
    public EventIDResult findEventById(String eventID, String authtoken) {
        Database db = new Database();
        try {
            db.openConnection();
            EventDAO eDao = new EventDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            Authtoken token = aDao.find(authtoken);
            if (token == null) {
                db.closeConnection(false);
                return new EventIDResult("Error: Invalid authtoken", false);
            }

            Event e = eDao.find(eventID);
            if (e == null) {
                db.closeConnection(false);
                return new EventIDResult("Error: Could not find event in database.", false);
            }

            if (!Objects.equals(e.getAssociatedUsername(), token.getUsername())) {
                db.closeConnection(false);
                return new EventIDResult("Error: Cannot access an event that is not related to the user.", false);
            }

            db.closeConnection(true);
            return new EventIDResult(e.getAssociatedUsername(), e.getEventID(), e.getPersonID(),
                    e.getLatitude(), e.getLongitude(), e.getCountry(), e.getCity(), e.getEventType(),
                    e.getYear(), true, null);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventIDResult("Exception was thrown while trying to find an event by ID.", false);
        }
    }
}
