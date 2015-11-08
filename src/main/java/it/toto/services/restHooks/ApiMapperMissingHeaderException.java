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
public class ApiMapperMissingHeaderException implements ExceptionMapper<MissingHeaderException> {

    @Override
    public Response toResponse(MissingHeaderException e) {

        log.error("Trapped an unexpected throws.", e);

        return Response.status(Response.Status.BAD_REQUEST).entity(ErrorResponse.of(e)).build();
    }

}
