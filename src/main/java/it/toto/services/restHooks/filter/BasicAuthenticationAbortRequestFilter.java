package it.toto.services.restHooks.filter;

import it.toto.services.restHooks.ApiHeaderUtils;
import it.toto.services.restHooks.AuthenticationType;
import it.toto.services.restHooks.annotation.BasicAuthenticated;
import it.toto.services.restHooks.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by toto on 08/11/15.
 */
@Slf4j
@BasicAuthenticated
public class BasicAuthenticationAbortRequestFilter extends AuthenticationAbortRequestFilter {

    @Inject
    public BasicAuthenticationAbortRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            @Context HttpServletRequest httpServletRequest) {
        super(apiHeaderUtils,apiResponse,httpServletRequest, AuthenticationType.BASIC);
    }
}
