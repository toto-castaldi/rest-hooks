package it.toto.services.restHooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by toto on 08/11/15.
 */
@AllArgsConstructor(staticName = "of")
public class CustomerEntity {

    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private Boolean admin;
    @Getter
    private Boolean customer;

}
