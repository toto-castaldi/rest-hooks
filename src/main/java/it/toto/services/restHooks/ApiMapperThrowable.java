package it.toto.services.restHooks;

import it.toto.services.restHooks.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by toto on 07/10/14.
 */
@Slf4j
@Provider
@Produces (MediaType.APPLICATION_JSON)
public class ApiMapperThrowable implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {

        log.error("Trapped an unexpected throws.", e);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorResponse.of(e)).build();
    }

}
