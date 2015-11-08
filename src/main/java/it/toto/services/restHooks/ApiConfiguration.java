package it.toto.services.restHooks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * Created by toto on 14/12/14.
 */
@Data
@AllArgsConstructor (staticName = "of")
public class ApiConfiguration {
    @NonNull
    private TimeUnit aliveTimeOutTimeUnit;
    @NonNull
    private Long aliveTimeOutAmount;
    @NonNull
    private TimeUnit waitForFirstPingTimeUnit;
    @NonNull
    private Long waitForFirstPingAmount;

}
