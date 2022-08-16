package Services;

import DataAccess.*;
import DataStorage.DataCache;
import DataStorage.LocationData;
import Model.Event;
import Model.Person;
import Model.User;
import Result.FillResult;

import java.util.List;
import java.util.Random;

/**
 * A class to generate a family tree when filling the databse.
 */
public class GenerateFamilyTree extends BaseService {

    /**
     * A count of person objects inserted into the database.
     */
    public int personCount;

    /**
     * A count of event objects inserted into the database.
     */
    public int eventCount;

    /**
     * The current year ot manipulate to get dates for events for all peresons in the tree.
     */
    int currentYear;

    /**
     * Constructor to create the tree.
     */
    public GenerateFamilyTree() {
        personCount = 0;
        eventCount = 0;
        currentYear = 2022;
    }

    /**
     * Generates a tree for the given user.
     *
     * @param user user to generate tree for.
     * @param gender gender of the user
     * @param generations number of generations to generate
     * @param db the database to get the connection
     * @return the fill result to the handler
     */
    public FillResult generateTree(User user, String gender, int generations, Database db) throws DataAccessException {
        PersonDAO pDao = new PersonDAO(db.getConnection());
        EventDAO eDao = new EventDAO(db.getConnection());
        Person userPerson = generateTreeHelper(user.getUsername(), gender, generations, db);

        pDao.deletePerson(userPerson.getPersonID()); // Delete the last person that was made so that there isn't duplicate person for the current user
        List<Event> events = eDao.findAllEventsForPerson(userPerson.getPersonID()); // and their events
        for (Event e : events) {
            eDao.deleteEvent(e.getEventID());
        }

        userPerson.setPersonID(user.getPersonID());
        userPerson.setAssociatedUsername(user.getUsername());
        userPerson.setFirstName(user.getFirstName());
        userPerson.setLastName(user.getLastName());
        userPerson.setGender(user.getGender());

        Random rand = new Random();
        int birthYear = currentYear - (rand.nextInt(35 - 30) + 30);
        LocationData.Location randLoc = getRandomLocation();
        eDao.insert(new Event(generateUniqueIdentifier(), user.getUsername(), user.getPersonID(), randLoc.getLatitude(), randLoc.getLongitude(),
                randLoc.getCountry(), randLoc.getCity(), "Birth", birthYear));
        eventCount++;

        pDao.insert(userPerson);
        personCount++;
        return new FillResult("Successfully added " + personCount + " persons and " + eventCount + " events to the database.", true);
    }

    /**
     * A recursive function to generate all the persons for a number of generations in a family tree.
     *
     * @param username of the user or person
     * @param gender of the user or person
     * @param generations number of generations to generate
     * @param db database to get connection
     * @return a new person that has been inserted in the database.
     */
    public Person generateTreeHelper(String username, String gender, int generations, Database db) {
        try {
            PersonDAO pDao = new PersonDAO(db.getConnection());
            EventDAO eDao = new EventDAO(db.getConnection());

            Person person = new Person();
            Person mother = null;
            Person father = null;

            if (generations > 0) {
                // subtract some random number from the current year to go back a generation
                Random rand = new Random();
                int years = rand.nextInt(35 - 30) + 30;
                currentYear = currentYear - years;

                mother = generateTreeHelper(username, "f", generations - 1, db);
                father = generateTreeHelper(username, "m", generations - 1, db);

                setSpouseIDs(mother, father, pDao);
                addMarriageEvents(eDao, mother, father, currentYear, username);

                years = rand.nextInt(35 - 30) + 30;
                currentYear = currentYear + years;
            }

            String personID = generateUniqueIdentifier();
            String firstName;
            if (gender.equals("f")) {
                firstName = getRandomFemaleName();
            } else {
                firstName = getRandomMaleName();
            }
            String lastName = getRandomSurName();

            person.setPersonID(personID);
            person.setAssociatedUsername(username);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setGender(gender);

            if (mother != null || father != null) {
                person.setMotherID(mother.getPersonID());
                person.setFatherID(father.getPersonID());
            }

            GenerateDeathAndBirth(eDao, person, currentYear, username);
            pDao.insert(person);
            personCount++;
            return person;

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new Person(null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Creates two separate marriage events for a couple. One for the father and one for the mother.
     *
     * @param eDao event DAO
     * @param mother mother
     * @param father father
     * @param currentYear current year that will be used when creating an event for a person
     * @param username user that the person is tied to.
     * @throws DataAccessException
     */
    private void addMarriageEvents(EventDAO eDao, Person mother, Person father, int currentYear, String username) throws DataAccessException {
        LocationData.Location randLoc = getRandomLocation();
        eDao.insert(new Event(generateUniqueIdentifier(), username, mother.getPersonID(), randLoc.getLatitude(), randLoc.getLongitude(),
                randLoc.getCountry(), randLoc.getCity(), "Marriage", currentYear));
        eventCount++;

        eDao.insert(new Event(generateUniqueIdentifier(), username, father.getPersonID(), randLoc.getLatitude(), randLoc.getLongitude(),
                randLoc.getCountry(), randLoc.getCity(), "Marriage", currentYear));
        eventCount++;
    }

    /**
     * Creates a birth and death event for a person and inserts the events to the database.
     *
     * @param eDao event DAO
     * @param person person to tie events to
     * @param currentYear current year to include in the events
     * @param username user that is tied to the events
     * @throws DataAccessException
     */
    private void GenerateDeathAndBirth(EventDAO eDao, Person person, int currentYear, String username) throws DataAccessException {
        Random rand = new Random();
        int birthYear = currentYear - (rand.nextInt(35 - 30) + 30);
        LocationData.Location randLoc = getRandomLocation();
        eDao.insert(new Event(generateUniqueIdentifier(), username, person.getPersonID(), randLoc.getLatitude(), randLoc.getLongitude(),
                randLoc.getCountry(), randLoc.getCity(), "Birth", birthYear));
        eventCount++;

        int deathYear = currentYear + (rand.nextInt(35 - 30) + 30);
        randLoc = getRandomLocation();
        eDao.insert(new Event(generateUniqueIdentifier(), username, person.getPersonID(), randLoc.getLatitude(), randLoc.getLongitude(),
                randLoc.getCountry(), randLoc.getCity(), "Death", deathYear));
        eventCount++;
    }

    /**
     * Set's a mother and father's spouse ID's to each others person ID's
     *
     * @param mother to set spouse ID
     * @param father to set spouse ID
     * @param pDao person DAO
     * @throws DataAccessException
     */
    private void setSpouseIDs(Person mother, Person father, PersonDAO pDao) throws DataAccessException {
        Person tempMom = pDao.find(mother.getPersonID());
        Person tempDad = pDao.find(father.getPersonID());
        pDao.deletePerson(mother.getPersonID());
        pDao.deletePerson(father.getPersonID());
        tempMom.setSpouseID(father.getPersonID());
        tempDad.setSpouseID(mother.getPersonID());
        pDao.insert(tempMom);
        pDao.insert(tempDad);
    }

    /**
     * Selects a random female name.
     *
     * @return the name
     */
    public String getRandomFemaleName() {
        String[] tempArray = DataCache.getFemaleNameData().getData();
        int index = new Random().nextInt(tempArray.length);
        return tempArray[index];
    }

    /**
     * Selects a random male name.
     *
     * @return the name
     */
    public String getRandomMaleName() {
        String[] tempArray = DataCache.getMaleNameData().getData();
        int index = new Random().nextInt(tempArray.length);
        return tempArray[index];
    }

    /**
     * Selects a random surname.
     *
     * @return the name
     */
    public String getRandomSurName() {
        String[] tempArray = DataCache.getSurnameData().getData();
        int index = new Random().nextInt(tempArray.length);
        return tempArray[index];
    }

    /**
     * Selects a random location.
     *
     * @return the event object.
     */
    public LocationData.Location getRandomLocation() {
        LocationData.Location[] tempArray = DataCache.getLocationData().getData();
        int index = new Random().nextInt(tempArray.length);
        return tempArray[index];
    }
}
