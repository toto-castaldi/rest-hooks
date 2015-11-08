package it.toto.services.restHooks;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by toto on 11/12/14.
 */
public class ApiValidation {

    private static final String PWD_TOKEN = "13354-PWD";

    public static boolean notNullAndBlank(String str) {
        return str != null && StringUtils.isBlank(str);
    }

    public static boolean notNullAndinvalidCap(String cap) {
        return cap != null && (StringUtils.length(cap) != 5 || !NumberUtils.isDigits(cap));
    }

    static String getPassword(String username, String password) {
        return DigestUtils.md5Hex(password + PWD_TOKEN + username);
    }

}
