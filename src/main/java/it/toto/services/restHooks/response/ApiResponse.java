package it.toto.services.restHooks.response;


import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.toto.services.restHooks.AuthenticationType;
import it.toto.services.restHooks.AuthorizationRequestInfo;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class ApiResponse {

    private static final String SLASH = "/";
    private static final String HEADER_LOCATION = "Location";

    @Inject
    public ApiResponse () {
    }

    /**
     * 200
     * @param entity
     * @return
     */
    public Response ok(Object entity) {
        if (entity instanceof  Iterable) {
            return Response.ok(Iterables.toArray((Iterable<?>) entity, Object.class)).build();
        } else {
            return Response.ok(entity).build();
        }

    }

    /**
     * 200
     * @return
     */
    public Response ok() {
        return Response.ok().build();
    }

    /**
     * 201
     * @param path
     * @return
     */
    public Response createdReturns(HttpServletRequest httpServletRequest, String... path) {

        URI location = getUri(httpServletRequest, path);

        return Response.created(location).entity(new String("{}")).build();
    }


    /**
     * 201
     * @param entity
     * @param path
     * @return
     */
    public Response createdReturns(HttpServletRequest httpServletRequest, Object entity, String... path) {
        URI location = getUri(httpServletRequest, path);

        return Response.status(201).header(HEADER_LOCATION, location).entity(entity).build();
    }

    /**
     * 204
     * @return
     */
    public Response noContent() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * 400
     * @param entity
     * @return
     */
    public Response badResponse(Object entity) {
        return Response.status(400).entity(entity).build();
    }

    public Response badResponse() {
        return Response.status(400).build();
    }

    /**
     * 401
     * @param entity
     * @return
     */
    public Response authenticationRequired(Object entity) {
        return Response.status(401).entity(entity).build();
    }

    /**
     * 403
     * @param entity
     * @return
     */
    public Response forbidden(Object entity) {
        return Response.status(403).entity(entity).build();
    }

    /**
     * 404
     * @return
     */
    public Response notFound() {
        return Response.status(404).build();
    }

    /**
     * 401
     * @param authorizationRequestes
     * @param responseBody
     * @return
     */
    public Response unauthorize(Iterable<AuthorizationRequestInfo> authorizationRequestes, Object responseBody) {
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED);
        if (Iterables.any(authorizationRequestes, new Predicate<AuthorizationRequestInfo>() {

            @Override
            public boolean apply(@Nullable AuthorizationRequestInfo authorizationRequest) {
                return authorizationRequest.getAuthenticationType() == AuthenticationType.BASIC;
            }
        }) || Iterables.isEmpty(authorizationRequestes)) {
            responseBuilder.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"AWS\"");
        };

        if (responseBody != null) {
            responseBuilder.entity(responseBody);
        }


        return responseBuilder.build();
    }

    /**
     * 501
     * @return
     */
    public Response notImplemented() {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    private URI getUri(HttpServletRequest httpServletRequest, String... subpath) {
        String uri = StringUtils.lowerCase(StringUtils.substringBefore(httpServletRequest.getProtocol(), SLASH));
        uri = uri +  "://";
        uri = uri +  httpServletRequest.getServerName();
        uri = uri +  ":";
        uri = uri +  httpServletRequest.getServerPort();
        List<String> paths = Lists.newArrayList();
        String contextPath = httpServletRequest.getContextPath();
        if (StringUtils.isNotBlank(contextPath)) {
            paths.add(StringUtils.substringAfter(contextPath,SLASH));
        }
        paths.addAll(Arrays.asList(subpath));
        uri = uri +  SLASH + Joiner.on(SLASH).join(paths);
        return URI.create(uri);
    }



}
