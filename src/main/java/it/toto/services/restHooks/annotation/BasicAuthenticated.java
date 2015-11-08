package it.toto.services.restHooks.annotation;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by toto on 08/11/15.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface BasicAuthenticated {

}
