package it.toto.services.restHooks.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by toto on 08/11/15.
 */
@AllArgsConstructor (staticName = "of")
public class SemaphoreStatusResponse {

    @Getter
    private boolean status;
}
