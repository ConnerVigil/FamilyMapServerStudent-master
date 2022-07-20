package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Person;
import Result.PersonResult;

import java.util.List;

/**
 * A class for the person web api call.
 */
public class PersonService {

    /**
     * Get all persons in the family tree of the user.
     *
     * @param authtoken required to get username.
     * @return personResult object
     */
    public PersonResult getAllPersons(String authtoken) {
        Database db = new Database();
        try {
            db.openConnection();
            PersonDAO pDao = new PersonDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            Authtoken token = aDao.find(authtoken);
            if (token == null) {
                db.closeConnection(false);
                return new PersonResult(null, false, "Error: Invalid authtoken");
            }

            List<Person> persons = pDao.findAllPersonsForUser(token.getUsername());
            Person[] personArray = new Person[persons.size()];
            persons.toArray(personArray);

            db.closeConnection(true);
            return new PersonResult(personArray, true, null);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonResult(null, false, "Exception thrown while trying to get all persons for a user.");
        }
    }

}
