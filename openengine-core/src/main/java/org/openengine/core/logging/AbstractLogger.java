package org.openengine.core.logging;

import org.openengine.core.EngineException;

public abstract class AbstractLogger {
    protected String loggerName;

    public final void setLoggerName(String desired) {
        if (loggerName != null)
            throw new EngineException("Logger Name already set");

        loggerName = desired;
    }

}
