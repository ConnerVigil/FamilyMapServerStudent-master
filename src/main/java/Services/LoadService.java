package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * A class to perform a load api call.
 */
public class LoadService {

    /**
     * Loads persons, users, and events into the database.
     *
     * @param request the request object
     * @return the Loadresult
     */
    public LoadResult load(LoadRequest request) {
        Database db = new Database();
        try {
            db.openConnection();
            UserDAO uDao = new UserDAO(db.getConnection());
            EventDAO eDao = new EventDAO(db.getConnection());
            PersonDAO pDao = new PersonDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());
            uDao.clear();
            eDao.clear();
            pDao.clear();
            aDao.clear();

            User[] users = request.getUsers();
            Person[] persons = request.getPersons();
            Event[] events = request.getEvents();

            int numUsersInserted = 0;
            for (User user : users) {
                uDao.insert(user);
                numUsersInserted++;
            }

            int numPersonsInserted = 0;
            for (Person person : persons) {
                pDao.insert(person);
                numPersonsInserted++;
            }

            int numEventsInserted = 0;
            for (Event event : events) {
                eDao.insert(event);
                numEventsInserted++;
            }

            db.closeConnection(true);
            return new LoadResult("Successfully added " + numUsersInserted + " users, " + numPersonsInserted + " persons, and " + numEventsInserted + " events to the database.", true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoadResult("Exception thrown while loading the database.", false);
        }
    }
}
