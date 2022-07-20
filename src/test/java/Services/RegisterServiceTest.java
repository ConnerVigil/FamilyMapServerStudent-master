package Services;

import DataAccess.*;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    private RegisterService service;
    private Database db;
    private EventDAO eDao;
    private AuthtokenDAO aDao;
    private UserDAO uDao;
    private User bestUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        service = new RegisterService();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());

        bestUser = new User("Conner", "s", "s", "s", "s", "s", "s");

        eDao.clear();
        aDao.clear();
        uDao.clear();
        db.closeConnection(true);
    }

    @Test
    public void registerPass() throws DataAccessException {
        db.openConnection();
        RegisterRequest request = new RegisterRequest("Conner", "2343", "test", "test", "test", "m");
        RegisterResult result = service.register(request);
        assertTrue(result.isSuccess());
        assertEquals("Conner", result.getUsername());
        db.closeConnection(false);
    }

    @Test
    public void registerFail() throws DataAccessException {
        db.openConnection();
        RegisterRequest request = new RegisterRequest("Conner", "2343", "test", "test", "test", "m");
        RegisterResult result = service.register(request);
        result = service.register(request);
        assertFalse(result.isSuccess());
        db.closeConnection(false);
    }
}