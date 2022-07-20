package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Event;
import Result.EventResult;

import java.util.List;

/**
 * A class to perform the event api call.
 */
public class EventService {

    /**
     * Get all the events tied to a particular user.
     *
     * @param authtoken needed to perform service
     * @return eventResult object.
     */
    public EventResult getAllEvents(String authtoken) {
        Database db = new Database();
        try {
            db.openConnection();
            EventDAO eDao = new EventDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            Authtoken token = aDao.find(authtoken);
            if (token == null) {
                db.closeConnection(false);
                return new EventResult(null, false, "Error: Invalid authtoken");
            }

            List<Event> events = eDao.findAllEventsForUser(token.getUsername());
            Event[] eventArray = new Event[events.size()];
            events.toArray(eventArray);

            db.closeConnection(true);
            return new EventResult(eventArray, true, null);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventResult(null, false, "Exception thrown while trying to get all events for a user.");
        }
    }
}
