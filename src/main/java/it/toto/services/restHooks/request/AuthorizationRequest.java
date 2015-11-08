package it.toto.services.restHooks.request;

/**
 * Created by toto on 11/12/14.
 */
public interface AuthorizationRequest {

    boolean isPassed();
    String getUsername();
    String getRequest();

}
