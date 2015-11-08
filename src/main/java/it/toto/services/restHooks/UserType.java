package it.toto.services.restHooks;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Created by toto on 08/11/15.
 */
public enum UserType {

    LIGHT,
    CUSTOMER (LIGHT),
    EXPIRED,
    REMOVED,
    ADMIN (LIGHT,CUSTOMER,REMOVED,EXPIRED),
    NONE;

    private final UserType[] delegates;

    UserType (UserType... delegates) {
        this.delegates = delegates;
    }

    public boolean isInRole(final UserType role) {
        return Iterables.tryFind(Arrays.asList(delegates), new Predicate<UserType>() {
            @Override
            public boolean apply(@Nullable UserType input) {
                return input == role;
            }
        }).isPresent() || role == this;
    }
}

