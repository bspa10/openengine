package org.openengine.engine.ecs;

import org.openengine.OpenEngineException;

public class ECSException extends OpenEngineException {

    public ECSException(String message, Throwable cause) {
        super(message, cause);
    }

    public ECSException(String message) {
        this(message, null);
    }

}
