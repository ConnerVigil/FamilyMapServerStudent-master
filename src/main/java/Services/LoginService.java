package Services;

import DataAccess.AuthtokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.Authtoken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * A class that logs in a user.
 */
public class LoginService extends BaseService {

    /**
     * Login the user
     *
     * @param request the request object
     * @return the loginResult object
     */
    public LoginResult login(LoginRequest request) {
        Database db = new Database();
        try {
            db.openConnection();
            UserDAO userDao = new UserDAO(db.getConnection());
            AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

            if (userDao.validate(request.getUsername(), request.getPassword())) {
                Authtoken authtoken = generateToken(request.getUsername());
                aDao.insert(authtoken);
                User user = userDao.find(request.getUsername());
                db.closeConnection(true);
                return new LoginResult(authtoken.getAuthtoken(), user.getUsername(), user.getPersonID(), true, null);

            } else {
                db.closeConnection(false);
                return new LoginResult("Error: Invalid username and password", false);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoginResult("Exception thrown while trying to log user in.", false);
        }
    }
}
