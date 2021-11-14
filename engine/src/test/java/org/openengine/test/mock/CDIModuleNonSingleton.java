package org.openengine.test.mock;

import org.openengine.cdi.Provider;

public class CDIModuleNonSingleton {

    @Provider(singleton = false)
    String hello(){
        return String.valueOf(System.currentTimeMillis());
    }

}
