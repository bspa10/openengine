package org.openengine.test.cdi.mock;

import org.openengine.cdi.Provider;

public class CDIModuleNoOverride {

    @Provider(overridable = false)
    public Integer integer() {
        return 0;
    }
}
