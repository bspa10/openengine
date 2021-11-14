package org.openengine.engine.renderer;

import org.openengine.core.EngineSettings;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggerFactory;

public abstract class Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    protected long window;

    public abstract void setBgColor(float red, float green, float blue, float alfa);

    protected abstract void doInit();
    public final void init() {
        LOGGER.debug("Initializing");
        doInit();
    }

    public abstract void hide();

    protected abstract void doRefresh();
    public final void render(){
        doRefresh();
    }

    public abstract void update();

    protected abstract void doCleanup();
    public final void cleanup() {
        LOGGER.debug("Destroying");
        doCleanup();
    }

    public abstract void setTitle(String title);

    public float getAspectRatio() {
        return (float) EngineSettings.getWindowSize().x / EngineSettings.getWindowSize().y;
    }
}
