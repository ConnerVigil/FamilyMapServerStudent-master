package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Event;
import Result.EventIDResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventIDServiceTest {

    private EventIDService service;
    private Database db;
    private Event bestEvent;
    private Authtoken bestAuthtoken;
    private EventDAO eDao;
    private AuthtokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new EventIDService();

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestAuthtoken = new Authtoken("123test", "Gale");

        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        aDao.clear();
        eDao.clear();
        eDao.insert(bestEvent);
        aDao.insert(bestAuthtoken);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        eDao.clear();
        aDao.clear();
        db.closeConnection(true);
    }

    @Test
    public void findEventByIdPass() {
        EventIDResult result = service.findEventById(bestEvent.getEventID(), bestAuthtoken.getAuthtoken());
        assertTrue(result.isSuccess());
        assertEquals(bestEvent.getEventID(), result.getEventID());
    }

    @Test
    public void findEventByIdFail() {
        EventIDResult result = service.findEventById(bestEvent.getEventID(), "thistokenisinvalid");
        assertFalse(result.isSuccess());

        result = service.findEventById("thiseventiddoesnotexist", "123test");
        assertFalse(result.isSuccess());
    }
}