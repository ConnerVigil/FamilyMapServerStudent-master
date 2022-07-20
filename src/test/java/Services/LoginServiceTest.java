package Services;

import DataAccess.*;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private LoginService service;
    private Database db;
    private UserDAO uDao;
    private User bestUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new LoginService();

        bestUser = new User("Conner", "123test", "s", "s", "s", "s", "s");

        db = new Database();
        db.openConnection();
        uDao = new UserDAO(db.getConnection());
        uDao.clear();
        uDao.insert(bestUser);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void loginPass() throws DataAccessException {
        db.openConnection();
        LoginRequest request = new LoginRequest("Conner", "123test");
        LoginResult result = service.login(request);
        assertTrue(result.isSuccess());
        assertEquals("Conner", result.getUsername());
    }

    @Test
    public void loginFail() throws DataAccessException {
        db.openConnection();
        LoginRequest request = new LoginRequest("invalid_username", "invalid_password");
        LoginResult result = service.login(request);
        assertFalse(result.isSuccess());
    }

}