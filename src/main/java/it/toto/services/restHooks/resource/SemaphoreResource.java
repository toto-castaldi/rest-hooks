package it.toto.services.restHooks.resource;

import it.toto.services.restHooks.ApiPath;
import it.toto.services.restHooks.MissingHeaderException;
import it.toto.services.restHooks.Semaphore;
import it.toto.services.restHooks.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by toto on 05/12/14.
 */
@Path(ApiPath.SEMAPHORE)
@Slf4j
public class SemaphoreResource {

    private static final String DEFAULT_APPLICATION = "app";
    private final ApiResponse apiResponse;
    private final Semaphore semaphore;

    @Inject
    public SemaphoreResource(
            ApiResponse apiResponse,
            Semaphore semaphore
    ) {
        this.apiResponse = apiResponse;
        this.semaphore = semaphore;
    }

    @POST
    @Path(ApiPath.ON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response on(
            @Context HttpServletRequest httpServletRequest,
            @HeaderParam("X-Mashape-Key") String mashapeKey
    ) {
        mashapeKey = StringUtils.defaultIfBlank(mashapeKey, DEFAULT_APPLICATION);
        if (StringUtils.isBlank(mashapeKey)) throw new MissingHeaderException("X-Mashape-Key");
        semaphore.on(mashapeKey);
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @POST
    @Path(ApiPath.OFF)
    @Produces(MediaType.APPLICATION_JSON)
    public Response off(
            @Context HttpServletRequest httpServletRequest,
            @HeaderParam("X-Mashape-Key") String mashapeKey
    ) {
        mashapeKey = StringUtils.defaultIfBlank(mashapeKey, DEFAULT_APPLICATION);
        if (StringUtils.isBlank(mashapeKey)) throw new MissingHeaderException("X-Mashape-Key");
        semaphore.off(mashapeKey);
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @POST
    @Path(ApiPath.SWITCH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchState(
            @Context HttpServletRequest httpServletRequest,
            @HeaderParam("X-Mashape-Key") String mashapeKey
    ) {
        mashapeKey = StringUtils.defaultIfBlank(mashapeKey, DEFAULT_APPLICATION);
        if (StringUtils.isBlank(mashapeKey)) throw new MissingHeaderException("X-Mashape-Key");
        log.info("mashape key = {}", mashapeKey);
        semaphore.switchState(mashapeKey);
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(
            @Context HttpServletRequest httpServletRequest,
            @HeaderParam("X-Mashape-Key") String mashapeKey
    ) {
        mashapeKey = StringUtils.defaultIfBlank(mashapeKey, DEFAULT_APPLICATION);
        if (StringUtils.isBlank(mashapeKey)) throw new MissingHeaderException("X-Mashape-Key");
        return apiResponse.ok(semaphore.status(mashapeKey));
    }

}