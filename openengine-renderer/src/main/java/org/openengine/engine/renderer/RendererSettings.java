package org.openengine.engine.renderer;

import org.joml.Matrix4f;

public abstract class RendererSettings {
    private RendererSettings(){}

    private static final Matrix4f frustum = new Matrix4f();
    public static Matrix4f getFrustum() {
        return RendererSettings.frustum;
    }

}
