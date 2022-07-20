package Services;

import DataAccess.AuthtokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.Authtoken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

/**
 * A class to register a user in the database.
 */
public class RegisterService extends BaseService {

    /**
     * Register a person in the database.
     *
     * @param request the request object
     * @return the RegisterResult object
     */
    public RegisterResult register(RegisterRequest request) {
        Database db = new Database();
        try {
            db.openConnection();
            UserDAO uDao = new UserDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            // Insert new user into database
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(),
                                 request.getLastName(), request.getGender(), generateUniqueIdentifier());

            if (uDao.find(request.getUsername()) != null) {
                db.closeConnection(false);
                return new RegisterResult("Error: Cannot register the same user twice.", false);
            }

            uDao.insert(user);

            // Log user in
            if (!uDao.validate(user.getUsername(), user.getPassword())) {
                db.closeConnection(false);
                return new RegisterResult("Error: Could not validate user after registering them.", false);
            }

            // Generate 4 generations of ancestor data
            GenerateFamilyTree tree = new GenerateFamilyTree();
            tree.generateTree(user, "m", 4, db);

            // return authtoken
            Authtoken token = generateToken(request.getUsername());
            aDao.insert(token);

            db.closeConnection(true);
            return new RegisterResult(token.getAuthtoken(), request.getUsername(), user.getPersonID(), true, null);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new RegisterResult("Exception thrown while trying to register.", false);
        }
    }
}
