package it.toto.services.restHooks;

import it.toto.services.restHooks.model.CustomerEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by toto on 11/12/14.
 */
public class ApiValidation {

    private static final String PWD_TOKEN = "13354-PWD";

    static String getPassword(String username, String password) {
        return DigestUtils.md5Hex(password + PWD_TOKEN + username);
    }

    public static boolean validCustomerPassword(CustomerEntity user, String password) {
        return StringUtils.equals(getPassword(user.getUsername(), password), user.getPassword());
    }

}
