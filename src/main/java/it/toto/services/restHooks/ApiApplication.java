package it.toto.services.restHooks;

import it.toto.commons.GuiceInjector;
import it.toto.services.restHooks.filter.CommonResponseHeaderFilter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;


/**
 * Created by toto on 07/10/14.
 */
@Slf4j
public class ApiApplication extends ResourceConfig {

    @Inject
    public ApiApplication(ServiceLocator serviceLocator) {
        String packageName = ApiMapperThrowable.class.getPackage().getName();
        log.debug("resource added {}", packageName);

        packages(packageName);

        register(JacksonFeature.class);
        register(ApiMapperThrowable.class);
        register(LoggingFilter.class);

        register(CommonResponseHeaderFilter.class);

        /** GUICE BRIDGE !!! **/
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(GuiceInjector.getIstance());

    }
}
