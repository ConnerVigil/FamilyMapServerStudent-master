package Result;

/**
 * A response body for the clear request from the web API.
 */
public class ClearResult extends BaseResult {

    /**
     * Constructor for clearresult response.
     *
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {
        super(message, success);
    }
}
