package com.github.totoCastaldi.services;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by toto on 14/12/14.
 */
@Singleton
@Slf4j
public class SwitchResourceSupport {

    public static final boolean DEFAULT_STATE = true;
    private Map<String, Boolean> statuses = Maps.newConcurrentMap();

    @Inject
    public SwitchResourceSupport(
    ) {
    }

    public void off(String key) {
        this.statuses.put(key, false);
    }

    public void on(String key) {
        this.statuses.put(key, true);
    }

    public boolean status(String key) {
        return BooleanUtils.toBooleanDefaultIfNull(this.statuses.get(key), DEFAULT_STATE);
    }

    public void switchState(String key) {
        this.statuses.put(key, ! status(key));
    }
}
