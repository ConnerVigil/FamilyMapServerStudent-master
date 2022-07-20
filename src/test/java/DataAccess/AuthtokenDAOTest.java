package DataAccess;

import Model.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthtokenDAOTest {

    private Database db;
    private Authtoken bestAuthtoken;
    private Authtoken bestAuthtoken2;
    private AuthtokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthtoken = new Authtoken("uhsdf894uiehw", "testing123");
        bestAuthtoken2 = new Authtoken("orsihfw49e8e", "testing321");
        Connection conn = db.getConnection();
        aDao = new AuthtokenDAO(conn);
        aDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        aDao.clear();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws DataAccessException {
        aDao.insert(bestAuthtoken);
        Authtoken compareTest = aDao.find(bestAuthtoken.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(bestAuthtoken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDao.insert(bestAuthtoken);
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuthtoken));
    }

    @Test
    public void findPass() throws DataAccessException {
        aDao.insert(bestAuthtoken);
        Authtoken test = aDao.find(bestAuthtoken.getAuthtoken());
        assertEquals(bestAuthtoken, test);
    }

    @Test
    public void findFail() throws DataAccessException {
        aDao.insert(bestAuthtoken);
        Authtoken test = aDao.find(bestAuthtoken2.getAuthtoken());
        assertNull(test);
    }

    @Test
    public void clear() throws DataAccessException {
        aDao.insert(bestAuthtoken2);
        aDao.clear();
        Authtoken test = aDao.find(bestAuthtoken2.getAuthtoken());
        assertNull(test);
    }

}
