package Services;

import DataAccess.*;
import Model.User;
import Result.FillResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

    private FillService service;
    private Database db;
    private EventDAO eDao;
    private AuthtokenDAO aDao;
    private UserDAO uDao;
    private User bestUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new FillService();

        bestUser = new User("Conner", "s", "s", "s", "s", "s", "s");

        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        eDao.clear();
        aDao.clear();
        uDao.clear();
        uDao.insert(bestUser);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        eDao.clear();
        aDao.clear();
        uDao.clear();
        db.closeConnection(true);
    }

    @Test
    public void fillPass() {
        service = new FillService();
        FillResult result = service.fill("Conner", 3);
        assertTrue(result.isSuccess());
    }

    @Test
    public void fillFail() throws DataAccessException {
        FillResult result = service.fill("test", 3);
        assertFalse(result.isSuccess());
    }
}