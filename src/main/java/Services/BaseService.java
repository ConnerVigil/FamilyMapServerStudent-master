package Services;

import Model.Authtoken;

import java.util.UUID;

public class BaseService {

    /**
     * Creates a new authtoken
     *
     * @return the authtoken object
     */
    public Authtoken generateToken(String username) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return new Authtoken(uuid, username);
    }

    /**
     * Creates a new unique identifier
     *
     * @return the unique string
     */
    public String generateUniqueIdentifier() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
