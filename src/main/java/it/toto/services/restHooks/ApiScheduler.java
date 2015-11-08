package it.toto.services.restHooks;

/**
 * Created by toto on 05/12/14.
 */

import it.toto.commons.Shutdownable;
import it.toto.commons.ShutdownableRepository;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by toto on 02/12/14.
 */
@Singleton
@Slf4j
public class ApiScheduler implements Shutdownable {

    private final ShutdownableRepository shutdownableRepository;

    @Delegate(excludes = ApiScheduler.ContainerScheduler.class)
    private Scheduler scheduler;

    @Inject
    public ApiScheduler(
            ShutdownableRepository shutdownableRepository
    ) {

        this.shutdownableRepository = shutdownableRepository;
    }


    public void start()  {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            shutdownableRepository.register(this);
            log.info("quartz started");
        } catch (SchedulerException e) {
            log.error("",e);
        }

    }

    @Override
    public void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error("",e);
        }
    }

    public interface ContainerScheduler {
        public void shutdown();
        public void start();
    }
}

