package org.openengine.test.mock;

import org.openengine.cdi.Provider;

public class CDIModuleNoOverride {

    @Provider(overridable = false)
    public Integer integer() {
        return 0;
    }
}
