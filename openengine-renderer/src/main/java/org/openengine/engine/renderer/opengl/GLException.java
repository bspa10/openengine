package org.openengine.engine.renderer.opengl;

import org.openengine.engine.renderer.RendererException;

public final class GLException extends RendererException {
    public GLException(String message, Throwable cause) {
        super(message, cause);
    }

    public GLException(String message) {
        this(message, null);
    }
}
