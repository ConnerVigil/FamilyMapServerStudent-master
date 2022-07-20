package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * A class to represent a load request for the web API.
 */
public class LoadRequest {

    private User[] users;
    private Person[] persons;
    private Event[] events;

    /**
     * Constructor for load request object.
     *
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
