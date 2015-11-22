package com.github.totoCastaldi.services;

import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by toto on 12/11/15.
 */
@Slf4j
public class MashapeHeaderCheck implements ContainerRequestFilter {

    private final String mashapeApiSecret;
    private final ApiResponse apiResponse;

    @Inject
    public MashapeHeaderCheck(
            ApiResponse apiResponse
    ) {
        this.apiResponse = apiResponse;

        mashapeApiSecret = System.getProperty("X-Mashape-Proxy-Secret");
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        String proxySecret = requestContext.getHeaderString("x-mashape-proxy-secret");

        log.info("check {} on {}", proxySecret, mashapeApiSecret);

        if (!StringUtils.equals(proxySecret, mashapeApiSecret)) {
            requestContext.abortWith(apiResponse.authenticationRequired(ErrorResponse.of("invalid source", "please use the service via Mashape https://market.mashape.com/toto/a-remote-switch-resource")));
        }
    }
}
