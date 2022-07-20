package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Person;
import Result.PersonIDResult;

import java.util.Objects;

/**
 *  A class for the personID web api call.
 */
public class PersonIDService {

    /**
     * Finds the person in the database with the personID.
     *
     * @param personID the person ID.
     * @param authtoken the authtoken that is required.
     * @return the personIDResult
     */
    public PersonIDResult findPersonById(String personID, String authtoken) {
        Database db = new Database();
        try {
            db.openConnection();
            PersonDAO pDao = new PersonDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            Authtoken token = aDao.find(authtoken);
            if (token == null) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Invalid authtoken", false);
            }

            Person p = pDao.find(personID);
            if (p == null) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Could not find person in database.", false);
            }

            if (!Objects.equals(p.getAssociatedUsername(), token.getUsername())) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Cannot access a person that is not related to the user.", false);
            }

            db.closeConnection(true);
            return new PersonIDResult(p.getAssociatedUsername(), p.getPersonID(), p.getFirstName(),
                    p.getLastName(), p.getGender(), p.getFatherID(), p.getMotherID(), p.getSpouseID(),
                    true, null);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonIDResult("Exception was thrown while trying to find a person by ID.", false);
        }
    }

}
