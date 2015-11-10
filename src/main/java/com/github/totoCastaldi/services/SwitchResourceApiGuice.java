package com.github.totoCastaldi.services;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import it.toto.services.restServer.ApiGuice;
import it.toto.services.restServer.model.CustomerDao;

/**
 * Created by toto on 09/11/15.
 */

public class SwitchResourceApiGuice extends ApiGuice {

    public SwitchResourceApiGuice() {
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
