package com.github.totoCastaldi.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.RestServerConf;
import com.github.totoCastaldi.restServer.plugin.MashapePlugin;
import com.google.inject.AbstractModule;

/**
 * Created by toto on 09/11/15.
 */

public class SwitchResourceApiGuice extends ApiServletContextListener {

    public SwitchResourceApiGuice() {
    }

    @Override
    protected RestServerConf getAppConf() {
        final RestServerConf.Builder builder = RestServerConf.builder();
        builder.add(new MashapePlugin("X-Mashape-Proxy-Secret"));
        builder.add(SwitchResource.class.getPackage());
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SwitchResourceSupport.class);
            }
        });
        builder.setBasicAutharization(MashapeCredentialResource.class);
        return builder.build();
    }

}
