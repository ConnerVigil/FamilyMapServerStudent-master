package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonDAOTest {

    private Database db;
    private Person bestPerson;
    private Person bestPerson2;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();

        bestPerson = new Person("0435897", "testy34", "Conner", "Bob",
                "m", "935024", "92385023", "023975");

        bestPerson2 = new Person("2039753", "lolol", "hi", "Bob",
                "f", "23047", "2394723", "923847");

        Connection conn = db.getConnection();
        pDao = new PersonDAO(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        // Assuming that each user has a unique personID
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person test = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, test);
    }

    @Test
    public void findFail() throws DataAccessException {
        pDao.insert(bestPerson);
        Person test = pDao.find(bestPerson2.getPersonID());
        assertNull(test);
    }

    @Test
    public void clear() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        Person test = pDao.find(bestPerson.getPersonID());
        assertNull(test);
    }

    @Test
    public void deletePersonPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.deletePerson(bestPerson.getPersonID());
        Person test = pDao.find(bestPerson.getPersonID());
        assertNull(test);
    }

    @Test
    public void deletePersonFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> pDao.deletePerson(bestPerson.getPersonID()));
    }

    @Test
    public void findAllPersonsForUserPass() throws DataAccessException {
        pDao.insert(bestPerson);
        List<Person> listOfEvents = pDao.findAllPersonsForUser("testy34");
        assertEquals(1, listOfEvents.size());
    }

    @Test
    public void findAllPersonsForUserFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.findAllPersonsForUser("Testusername"));
    }
}
