package it.toto.services.restHooks.resource;

import it.toto.services.restHooks.ApiCurrentExecution;
import it.toto.services.restHooks.ApiPath;
import it.toto.services.restHooks.Semaphore;
import it.toto.services.restHooks.annotation.BasicAuthenticated;
import it.toto.services.restHooks.annotation.UserProfileCustomer;
import it.toto.services.restHooks.response.ApiResponse;
import it.toto.services.restHooks.response.SemaphoreStatusResponse;
import lombok.extern.slf4j.Slf4j;

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

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ApiPath.ON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response on(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        semaphore.on(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ApiPath.OFF)
    @Produces(MediaType.APPLICATION_JSON)
    public Response off(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        semaphore.off(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ApiPath.SWITCH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchState(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        semaphore.switchState(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ApiPath.SEMAPHORE);
    }

    @GET
    @UserProfileCustomer
    @BasicAuthenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        return apiResponse.ok(SemaphoreStatusResponse.of(semaphore.status(apiCurrentExecution.getUsername().get())));
    }

}