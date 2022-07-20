package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.ClearResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private ClearService service;
    private Database db;
    private User bestUser;
    private Authtoken bestAuthtoken;
    private Person bestPerson;
    private Event bestEvent;
    private UserDAO uDao;
    private AuthtokenDAO aDao;
    private EventDAO eDao;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new ClearService();

        bestUser = new User("Biking_123A", "Gale", "Gale123A",
                "Bob", "Joe", "m", "123456789");

        bestAuthtoken = new Authtoken("uhsdf894uiehw", "testing123");

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestPerson = new Person("0435897", "testy34", "Conner", "Bob",
                "m", "935024", "92385023", "023975");

        db = new Database();
        db.openConnection();
        uDao = new UserDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        uDao.insert(bestUser);
        aDao.insert(bestAuthtoken);
        eDao.insert(bestEvent);
        pDao.insert(bestPerson);
        db.closeConnection(true);
    }

    @Test
    public void clearPass() throws DataAccessException {
        ClearResult result = service.clear();
        assertEquals(true, result.isSuccess());

        db.openConnection();
        uDao = new UserDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        assertNull(uDao.find(bestUser.getUsername()));
        assertNull(aDao.find(bestAuthtoken.getAuthtoken()));
        assertNull(eDao.find(bestEvent.getEventID()));
        assertNull(pDao.find(bestPerson.getPersonID()));
        db.closeConnection(false);
    }
}