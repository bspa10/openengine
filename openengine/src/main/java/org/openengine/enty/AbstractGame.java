package org.openengine.enty;

import org.openengine.cdi.Injector;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggerFactory;
import org.openengine.engine.Engine;
import org.openengine.platform.PlatformLinuxModule;
import org.openengine.reflection.CLTools;

/**
 * Game code Entry Point.
 */
public abstract class AbstractGame {
    private final Logger LOGGER = LoggerFactory.getLogger(AbstractGame.class);

    static {
        final var os = System.getProperty("os.name");
        if (os.contains("nix") || os.contains("nux")) {
            Injector.register(PlatformLinuxModule.class);
        }
    }

    private final Engine engine;

    public AbstractGame() {
        final var classes = CLTools.getAllKnownClassesName();
        if (classes.contains("org.openengine.engine.renderer.GLModule"))
            try {
                LOGGER.info("Using OpenGL Binding");
                Injector.register(Class.forName("org.openengine.engine.renderer.GLModule"));
            } catch (Exception exception) {
                LOGGER.fatal("Unable to load engine binding [%s]", exception.getLocalizedMessage());
            }

        engine = new Engine();
    }

    public void start() {
        engine.start();
    }

    public void stop() {
        engine.stop();
    }
}
