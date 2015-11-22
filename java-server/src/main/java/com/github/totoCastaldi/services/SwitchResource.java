package com.github.totoCastaldi.services;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.annotation.BasicAuthenticated;
import com.github.totoCastaldi.restServer.annotation.UserProfileCustomer;
import com.github.totoCastaldi.restServer.response.ApiResponse;
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
@Path(SwitchResourceApiPath.SEMAPHORE)
@Slf4j
public class SwitchResource {

    private final ApiResponse apiResponse;
    private final SwitchResourceSupport switchResourceSupport;

    @Inject
    public SwitchResource(
            ApiResponse apiResponse,
            SwitchResourceSupport switchResourceSupport
    ) {
        this.apiResponse = apiResponse;
        this.switchResourceSupport = switchResourceSupport;
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(SwitchResourceApiPath.ON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response on(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        switchResourceSupport.on(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, SwitchResourceApiPath.SEMAPHORE);
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(SwitchResourceApiPath.OFF)
    @Produces(MediaType.APPLICATION_JSON)
    public Response off(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        switchResourceSupport.off(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, SwitchResourceApiPath.SEMAPHORE);
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(SwitchResourceApiPath.SWITCH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchState(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        switchResourceSupport.switchState(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, SwitchResourceApiPath.SEMAPHORE);
    }

    @GET
    @UserProfileCustomer
    @BasicAuthenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        return apiResponse.ok(SwitchResourceResponse.of(switchResourceSupport.status(apiCurrentExecution.getUsername().get())));
    }

}