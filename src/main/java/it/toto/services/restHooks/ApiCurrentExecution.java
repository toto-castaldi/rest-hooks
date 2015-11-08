package it.toto.services.restHooks;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by toto on 11/12/14.
 */
@RequiredArgsConstructor(staticName = "on")
public class ApiCurrentExecution {

    private enum KEY {};

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

    private <T> T getProperty(KEY key) {
        return (T) currentHttpRequest.getAttribute(key.name());
    }

    private void setProperty(KEY key, Object obj) {
        currentHttpRequest.setAttribute(key.name(), obj);
    }
}

