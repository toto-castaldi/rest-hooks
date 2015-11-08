package it.toto.services.restHooks;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * Created by toto on 13/10/14.
 */
@Slf4j
public class ApiChangeTypeFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        MultivaluedMap<String, String> headers = clientResponseContext.getHeaders();

        clientResponseContext.getHeaders().putSingle("Content-Type", "application/json");

        //log.info("headers {}", headers);
    }
}
