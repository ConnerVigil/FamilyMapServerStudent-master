package Result;

import Model.Event;

/**
 * A class representing the event response from web API.
 */
public class EventResult extends BaseResult {

    private Event[] data;

    /**
     * A constructor for the EventResult object.
     *
     * @param data
     * @param success
     * @param message
     */
    public EventResult(Event[] data, boolean success, String message) {
        super(message, success);
        this.data = data;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
