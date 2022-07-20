package Services;

import DataAccess.*;
import Result.ClearResult;

/**
 * A class to perform the clear api web call.
 */
public class ClearService {

    /**
     * Clears the entire database.
     *
     * @return a result object.
     */
    public ClearResult clear() {
        Database db = new Database();

        try {
            db.openConnection();
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());
            UserDAO uDao = new UserDAO(db.getConnection());
            PersonDAO pDao = new PersonDAO(db.getConnection());
            EventDAO eDao = new EventDAO(db.getConnection());

            aDao.clear();
            uDao.clear();
            pDao.clear();
            eDao.clear();

            db.closeConnection(true);
            return new ClearResult("Clear succeeded", true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new ClearResult("ClearService was not able to clear the database", false);
        }
    }
}
