package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

    private LoadService service;
    private Database db;
    private EventDAO eDao;
    private PersonDAO pDao;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        service = new LoadService();
        eDao = new EventDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        eDao.clear();
        uDao.clear();
        pDao.clear();
        db.closeConnection(true);
    }

    @Test
    public void loadPass() throws DataAccessException {
        User user1 = new User("s", "s", "s", "s", "s", "s", "s");
        User user2 = new User("r", "r", "r", "r", "r", "r", "r");
        User user3 = new User("t", "t", "t", "t", "t", "t", "t");
        User[] users = new User[]{user1, user2, user3};

        Person person1 = new Person("a", "a", "a", "a", "a", "a", "a", "a");
        Person person2 = new Person("b", "b", "b", "b", "b", "b", "b", "b");
        Person person3 = new Person("c", "c", "c", "c", "c", "c", "c", "c");
        Person[] persons = new Person[]{person1, person2,person3};

        Event event1 = new Event("q", "q", "q", 5.6f, 5.6f, "q", "q", "q", 2022);
        Event event2 = new Event("w", "w", "w", 5.6f, 5.6f, "w", "w", "w", 2022);
        Event event3 = new Event("e", "e", "e", 5.6f, 5.6f, "e", "e", "e", 2022);
        Event[] events = new Event[]{event1, event2, event3};

        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());

        LoadRequest request = new LoadRequest(users, persons, events);
        LoadResult result = service.load(request);
        assertTrue(result.isSuccess());
        assertNotNull(uDao.find("s"));
        assertNotNull(uDao.find("r"));
        assertNotNull(uDao.find("t"));

        assertNotNull(pDao.find("a"));
        assertNotNull(pDao.find("b"));
        assertNotNull(pDao.find("c"));

        assertNotNull(eDao.find("q"));
        assertNotNull(eDao.find("w"));
        assertNotNull(eDao.find("e"));
        db.closeConnection(false);
    }
}