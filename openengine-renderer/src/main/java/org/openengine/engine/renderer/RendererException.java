package org.openengine.engine.renderer;

import org.openengine.OpenEngineException;

public class RendererException extends OpenEngineException {

    public RendererException(String message, Throwable cause) {
        super(message, cause);
    }

    public RendererException(String message) {
        this(message, null);
    }

}
