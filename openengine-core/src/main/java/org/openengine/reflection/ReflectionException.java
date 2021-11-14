package org.openengine.reflection;

import org.openengine.OpenEngineException;

public class ReflectionException extends OpenEngineException {

    public ReflectionException(String message) {
        this(message, null);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
