package it.toto.commons;

import java.util.Collection;

/**
 * Created by toto on 05/12/14.
 */
public interface ShutdownableRepository {

    void register(Shutdownable shutdownable);

    Collection<Shutdownable> getServices();

}
