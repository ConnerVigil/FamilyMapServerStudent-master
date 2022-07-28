package Result;

/**
 * A class to represent a load response from the web API.
 */
public class LoadResult extends BaseResult {

    /**
     * A constructor for the LoadResult object.
     *
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
        super(message, success);
    }
}
