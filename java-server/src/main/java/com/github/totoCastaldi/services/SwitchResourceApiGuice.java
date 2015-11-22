package com.github.totoCastaldi.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.List;

/**
 * Created by toto on 09/11/15.
 */

public class SwitchResourceApiGuice extends ApiServletContextListener {

    public SwitchResourceApiGuice() {
    }

    @Override
    protected List<Class<? extends ContainerRequestFilter>> getContainerRequestFilters() {
        return Lists.newArrayList(MashapeHeaderCheck.class);
    }

    @Override
    protected List<Package> getPackages() {
        return Lists.newArrayList(SwitchResource.class.getPackage());
    }

    @Override
    protected String getPasswordSeed() {
        return "13354-PWD";
    }

    @Override
    protected Class<? extends CustomerDao> getCustomerDaoClass() {
        return SwitchResourceCustomerDao.class;
    }

    @Override
    public Module getAppModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(SwitchResourceSupport.class);
            }
        };
    }
}
