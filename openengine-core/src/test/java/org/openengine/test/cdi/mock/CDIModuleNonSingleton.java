package org.openengine.test.cdi.mock;

import org.openengine.cdi.Provider;

public class CDIModuleNonSingleton {

    @Provider(singleton = false)
    String hello(){
        return String.valueOf(System.currentTimeMillis());
    }

}
