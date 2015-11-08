package it.toto.services.restHooks.filter;

import com.google.common.collect.Lists;
import it.toto.services.restHooks.*;
import it.toto.services.restHooks.request.AuthorizationRequest;
import it.toto.services.restHooks.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.List;

/**
 * Created by toto on 08/11/15.
 */
@Slf4j
public class AuthenticationAndProfileRequestFilter implements ContainerRequestFilter {

    private final ApiHeaderUtils apiHeaderUtils;
    private final ApiResponse apiResponse;
    private final UserProfile userProfile;
    private final HttpServletRequest httpServletRequest;

    @Inject
    public AuthenticationAndProfileRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            UserProfile userProfile,
            @Context HttpServletRequest httpServletRequest
    ) {
        this.apiHeaderUtils = apiHeaderUtils;
        this.apiResponse = apiResponse;
        this.userProfile = userProfile;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorizationHeader)) {
            log.info("login {}", authorizationHeader);

            ApiCurrentExecution currentExecution = ApiCurrentExecution.on(httpServletRequest);

            Iterable<AuthorizationRequestInfo> requests = apiHeaderUtils.parseAuthorization(authorizationHeader);

            boolean passed = false;
            String username = null;

            List<AuthenticationType> authenticationPassed = Lists.newArrayList();
            List<AuthenticationType> authenticationNotPassed = Lists.newArrayList();

            for (AuthorizationRequestInfo request : requests) {
                AuthorizationRequest authorizationRequest = request.getAuthorizationRequest();
                final AuthenticationType authenticationType = request.getAuthenticationType();
                if (authorizationRequest.isPassed()) {
                    username = authorizationRequest.getUsername();
                    authenticationPassed.add(authenticationType);
                    passed = true;
                } else {
                    authenticationNotPassed.add(authenticationType);
                }
            }
            if (passed) {
                currentExecution.setUsername(username);
                currentExecution.authenticationPassed(authenticationPassed);
                currentExecution.authenticationNotPassed(authenticationNotPassed);
                currentExecution.setUserType(userProfile.resolve(username).get());
            }
        } else {
            log.debug("no authorization header {}", containerRequestContext.getHeaders());
        }

    }
}

