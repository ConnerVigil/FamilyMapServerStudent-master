package DataAccess;

import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User bestUser;
    private User bestUser2;
    private UserDAO uDao;
    private AuthtokenDAO aDao;
    private EventDAO eDao;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        bestUser = new User("Biking_123A", "Gale", "Gale123A",
                "Bob", "Joe", "m", "123456789");

        bestUser2 = new User("helloworld", "123", "Gale123A",
                "Joe", "Bob", "m", "348925678349");

        Connection conn = db.getConnection();
        uDao = new UserDAO(conn);
        aDao = new AuthtokenDAO(conn);
        eDao = new EventDAO(conn);
        pDao = new PersonDAO(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        User test = uDao.find(bestUser.getUsername());
        assertEquals(bestUser, test);
    }

    @Test
    public void findFail() throws DataAccessException {
        uDao.insert(bestUser);
        User test = uDao.find(bestUser2.getUsername());
        assertNull(test);
    }

    @Test
    public void clear() throws DataAccessException {
        uDao.insert(bestUser2);
        uDao.clear();
        User test = uDao.find(bestUser2.getUsername());
        assertNull(test);
    }

    @Test
    public void validatePass() throws DataAccessException {
        uDao.insert(bestUser);
        boolean test = uDao.validate(bestUser.getUsername(), bestUser.getPassword());
        assertTrue(test);
    }

    @Test
    public void validateFail() throws DataAccessException {
        uDao.insert(bestUser);
        boolean test = uDao.validate(bestUser.getUsername(), "thisshouldfail");
        assertFalse(test);
    }

    @Test
    public void deleteDataForUserPass() throws DataAccessException {
        aDao.insert(new Authtoken("9fh9834y943", "testusername"));
        pDao.insert(new Person("f", "testusername", "d", "d", "d", "d", "d", "d"));
        uDao.insert(new User("testusername", "s", "s", "s", "s", "s", "s"));
        eDao.insert(new Event("f", "testusername", "d", 587.675f, 587.675f, "d", "d", "d", 2000));
        uDao.deleteDataForUser("testusername");
        assertNull(aDao.find("9fh9834y943"));
        assertNull(pDao.find("f"));
        assertNull(eDao.find("f"));
    }

    @Test
    public void deleteDataForUserFail() {
        assertThrows(DataAccessException.class, () -> uDao.deleteDataForUser("doesnotexist"));
    }
}
