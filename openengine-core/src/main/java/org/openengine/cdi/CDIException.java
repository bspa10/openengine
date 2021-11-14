package org.openengine.cdi;

import org.openengine.OpenEngineException;

public final class CDIException extends OpenEngineException {

    public CDIException(String message) {
        this(message, null);
    }

    public CDIException(String message, Throwable cause) {
        super(message, cause);
    }
}
