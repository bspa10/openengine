package org.openengine.engine.input;

import org.openengine.OpenEngineException;

public class InputException extends OpenEngineException {

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputException(String message) {
        this(message, null);
    }

}
