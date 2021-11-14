package org.openengine.platform;

import org.openengine.cdi.Provider;
import org.openengine.core.logging.Logger;
import org.openengine.platform.logging.ConsoleLogger;
import org.openengine.reflection.ClassUtils;

public final class PlatformLinuxModule {

    @Provider(overridable = false, singleton = false)
    public Logger logger() {
        return ClassUtils.newInstance(ConsoleLogger.class);
    }

}
