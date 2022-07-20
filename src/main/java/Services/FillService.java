package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import Result.FillResult;

/**
 * A class to perform the event fill call.
 */
public class FillService {

    /**
     * Fill's the database with persons and events, for the particular user.
     *
     * @param username the user
     * @param numGenerations number of generations to create.
     * @return Fillresult response object
     */
    public FillResult fill(String username, int numGenerations) {
        Database db = new Database();
        try {
            db.openConnection();
            // Initialize DAO's
            UserDAO uDao = new UserDAO(db.getConnection());
            User curUser = uDao.find(username);

            // Check to make sure the user is already registered in database
            if (curUser == null) {
                db.closeConnection(false);
                return new FillResult("Cannot fill username: " + username + " because it was not in the database.", false);
            }

            // If there is any data in the database already associated with the given username, it is deleted
            uDao.deleteDataForUser(username);

            if (numGenerations == 0) {
                GenerateFamilyTree tree = new GenerateFamilyTree();
                FillResult result = tree.generateTree(curUser, "m", numGenerations, db);
                db.closeConnection(true);
                return result;

            } else if (numGenerations > 0) {
                // Custom generations amount
                GenerateFamilyTree tree = new GenerateFamilyTree();
                FillResult result = tree.generateTree(curUser, "m", numGenerations, db);
                db.closeConnection(true);
                return result;

            } else {
                db.closeConnection(false);
                return new FillResult("Not a valid number of generations.", false);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new FillResult("Exception thrown while trying to fill the database.", false);
        }
    }
}
