package org.openengine.engine;

import org.openengine.OpenEngineException;

public class EngineException extends OpenEngineException {

    public EngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineException(String message) {
        this(message, null);
    }
}
