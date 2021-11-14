package org.openengine.core.logging;

import org.openengine.cdi.Injector;
import org.openengine.core.EngineException;

public abstract class LoggerFactory {
    private LoggerFactory() {}

    public static Logger getLogger(Class<?> klass) {
        final var logger = Injector.get(Logger.class);
        if ( ! (logger instanceof AbstractLogger)) {
            throw new EngineException("%s does not extends %s".formatted(logger.getClass().getTypeName(), AbstractLogger.class.getTypeName()));
        }

        ((AbstractLogger) logger).setLoggerName(klass.getTypeName());
        return logger;
    }

}
