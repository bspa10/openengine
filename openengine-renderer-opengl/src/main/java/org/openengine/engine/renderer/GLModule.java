package org.openengine.engine.renderer;

import org.openengine.cdi.Provider;
import org.openengine.engine.renderer.opengl.GLWindow;
import org.openengine.reflection.ClassUtils;

public class GLModule {

    @Provider
    public Window glwindow() {return ClassUtils.newInstance(GLWindow.class);}

}
