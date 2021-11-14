package org.openengine;

public abstract class OpenEngineException extends RuntimeException {

    public OpenEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenEngineException(String message) {
        this(message, null);
    }
}
