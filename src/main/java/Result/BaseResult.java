package Result;

/**
 * A base response body for the other request classes to inherit from.
 */
public class BaseResult {

    private String message;
    private boolean success;

    /**
     * Constructor for result response.
     *
     * @param message
     * @param success
     */
    public BaseResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
