package it.toto.services.restHooks;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by toto on 11/12/14.
 */
@RequiredArgsConstructor(staticName = "on")
public class ApiCurrentExecution {

    private enum KEY {AUTHENTICATION_NOT_PASSED, USERNAME, USER_TYPE, AUTHENTICATION_PASSED};

    @NonNull
    private final HttpServletRequest currentHttpRequest;


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (KEY value : KEY.values()) {
            stringBuffer.append(value.name()).append("=").append(getProperty(value)).append(";");
        }
        return stringBuffer.toString();
    }

    public Optional<String> getUsername() {
        return Optional.fromNullable((String) getProperty(KEY.USERNAME));
    }


    private <T> T getProperty(KEY key) {
        return (T) currentHttpRequest.getAttribute(key.name());
    }

    private void setProperty(KEY key, Object obj) {
        currentHttpRequest.setAttribute(key.name(), obj);
    }

    public boolean isAuthenticationPassed() {
        return Iterables.size(getAuthenticationPassedType()) > 0;
    }

    public Iterable<AuthenticationType> getAuthenticationPassedType() {
        Iterable<AuthenticationType> result = getProperty(KEY.AUTHENTICATION_PASSED);
        if (result == null) {
            result = Lists.newArrayList();
        }
        return result;
    }

    public void authenticationNotPassed(Iterable<AuthenticationType> authenticationType) {
        setProperty(ApiCurrentExecution.KEY.AUTHENTICATION_NOT_PASSED, authenticationType);
    }

    public void setUsername(String username) {
        setProperty(ApiCurrentExecution.KEY.USERNAME, username);
    }

    public void setUserType(UserType userType) {
        setProperty(ApiCurrentExecution.KEY.USER_TYPE, userType);
    }

    public void authenticationPassed(Iterable<AuthenticationType> authenticationType) {
        setProperty(ApiCurrentExecution.KEY.AUTHENTICATION_PASSED, authenticationType);
    }

    public Optional<UserType> getUserType() {
        return Optional.fromNullable((UserType) getProperty(KEY.USER_TYPE));
    }

}

