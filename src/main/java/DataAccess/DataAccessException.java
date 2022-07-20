package DataAccess;

/**
 * An exception to through instead of SQL exception.
 */
public class DataAccessException extends Exception {
    DataAccessException(String message) {
        super(message);
    }
}
