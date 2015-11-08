package it.toto.services.restHooks;

import lombok.Getter;

/**
 * Created by goto10 on 08/11/2015.
 */
public class MissingHeaderException extends RuntimeException {

    @Getter
    private String header;

    public MissingHeaderException(String header) {
        this.header = header;
    }

    @Override
    public String getMessage() {
        return "missing header [" + header + "]";
    }
}
