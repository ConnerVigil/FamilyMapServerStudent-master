package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Person;
import Model.User;
import Result.PersonResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService service;
    private Database db;
    private AuthtokenDAO aDao;
    private UserDAO uDao;
    private PersonDAO pDao;
    private User bestUser;
    private Authtoken bestAuthtoken;
    private Person p1;
    private Person p2;
    private Person p3;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        service = new PersonService();
        aDao = new AuthtokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());

        bestUser = new User("Conner", "s", "s", "s", "s", "s", "s");
        bestAuthtoken = new Authtoken("123test", "Conner");
        p1 = new Person("test1", "Conner", "test1", "test", "test", "test", "test", "test");
        p2 = new Person("test2", "Conner", "test2", "test", "test", "test", "test", "test");
        p3 = new Person("test3", "Conner", "test3", "test", "test", "test", "test", "test");

        aDao.clear();
        uDao.clear();
        pDao.clear();
        uDao.insert(bestUser);
        aDao.insert(bestAuthtoken);
        pDao.insert(p1);
        pDao.insert(p2);
        pDao.insert(p3);
        db.closeConnection(true);
    }

    @Test
    public void getAllPersonsPass() throws DataAccessException {
        db.openConnection();
        PersonResult result = service.getAllPersons(bestAuthtoken.getAuthtoken());
        Person[] personArray = result.getData();
        assertEquals(3, personArray.length);
        db.closeConnection(false);
    }

    @Test
    public void getAllPersonsFail() throws DataAccessException {
        db.openConnection();
        PersonResult result = service.getAllPersons("Invalid_authtoken");
        assertNull(result.getData());
        assertFalse(result.isSuccess());
        db.closeConnection(false);
    }

}