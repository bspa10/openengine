package org.openengine.test.cdi.mock;

import org.openengine.cdi.Provider;

public class CDIModuleOverridable {
    @Provider
    public String string() {
        return "";
    }
}
