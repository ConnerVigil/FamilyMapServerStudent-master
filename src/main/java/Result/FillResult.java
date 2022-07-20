package Result;

/**
 * A fill response body from the web API.
 */
public class FillResult extends BaseResult {

    /**
     * A constructor for the FillResult object.
     *
     * @param message
     * @param success
     */
    public FillResult(String message, boolean success) {
        super(message, success);
    }
}
