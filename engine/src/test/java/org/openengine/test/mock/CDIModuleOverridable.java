package org.openengine.test.mock;

import org.openengine.cdi.Provider;

public class CDIModuleOverridable {
    @Provider
    public String string() {
        return "";
    }
}
