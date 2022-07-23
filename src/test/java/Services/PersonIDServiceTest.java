package Services;

import DataAccess.*;
import Model.Authtoken;
import Model.Person;
import Result.PersonIDResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonIDServiceTest {

    private PersonIDService service;
    private Database db;
    private PersonDAO pDao;
    private AuthtokenDAO aDao;
    private Person bestPerson;
    private Authtoken bestAuthtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        service = new PersonIDService();
        pDao = new PersonDAO(db.getConnection());
        aDao = new AuthtokenDAO(db.getConnection());

        bestPerson = new Person("1234", "condezzy", "s", "s", "s", "s", "s", "9384rh4");
        bestAuthtoken = new Authtoken("123test", "condezzy");

        pDao.clear();
        aDao.clear();

        aDao.insert(bestAuthtoken);
        pDao.insert(bestPerson);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void findPersonByIdPass() throws DataAccessException {
        db.openConnection();
        PersonIDResult result = service.findPersonById(bestPerson.getPersonID(), bestAuthtoken.getAuthtoken());
        assertTrue(result.isSuccess());
    }

    @Test
    public void findPersonByIdFail() throws DataAccessException {
        db.openConnection();
        PersonIDResult result = service.findPersonById(bestPerson.getPersonID(), "invalid_authtoken");
        assertFalse(result.isSuccess());
    }

}