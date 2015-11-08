package it.toto.commons;

import com.google.inject.Injector;

/**
 * Created by toto on 05/12/14.
 */
public class GuiceInjector {

    private static Injector istance;

    public static void setIstance(Injector istance) {
        GuiceInjector.istance = istance;
    }

    public static Injector getIstance() {
        return istance;
    }
}
