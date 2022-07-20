package Services;

import DataAccess.AuthtokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.Authtoken;
import Model.Event;
import Result.EventResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private EventService service;
    private Database db;
    private Event bestEvent;
    private Event bestEvent2;
    private EventDAO eDao;
    private AuthtokenDAO aDao;
    private Authtoken authtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new EventService();

        bestEvent = new Event("Biking_123A", "conner", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestEvent2 = new Event("sdhf439re", "conner", "o348hrjkr",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2010);

        authtoken = new Authtoken("39408hosdjf", "conner");

        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());
        eDao.insert(bestEvent);
        eDao.insert(bestEvent2);
        aDao.insert(authtoken);
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
    public void getAllEventsPass() {
        EventResult result = service.getAllEvents("39408hosdjf");
        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().length);
    }

    @Test
    public void getAllEventsFail() {
        EventResult result = service.getAllEvents("invalidauthtoken");
        assertFalse(result.isSuccess());
    }
}