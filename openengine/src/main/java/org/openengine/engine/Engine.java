package org.openengine.engine;

import org.openengine.EngineState;
import org.openengine.cdi.Injector;
import org.openengine.core.EngineSettings;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggerFactory;
import org.openengine.engine.renderer.Window;

public final class Engine {
    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);
    public static final long NANOSECOND = 1000000000L;

    private final double frametime = 1.0 / EngineSettings.getFramerate();

    private final Window window = Injector.get(Window.class);

    public void start() {
        if (EngineState.isRunning()) {
            LOGGER.warn("Trying to start an on-going game");
            return;
        }

        window.init();
        run();
    }

    public void stop() {
        if (!EngineState.isRunning())
            return;

        LOGGER.info("Stopping the Game Engine");
        EngineState.setStopped();
    }

    private void run() {
        EngineState.setRunning();
        var frames = 0;
        var frameCounter = 0L;
        var lastTime = System.nanoTime();
        var unprocessedTime = 0.0;

        while (EngineState.isRunning()) {
            // Marca o instante de inicio do loop
            final var startTime = System.nanoTime();

            // Identifica o tempo decorrido entre o último loop e este
            final var passedTime = startTime - lastTime;

            // Armazena o tempo de início do loop
            lastTime = startTime;

            // Frame elapsed time
            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            // Processa as entradas do usuário
            processInput();

            var shouldRender = false;
            while (unprocessedTime  > frametime) {
                shouldRender = true;
                unprocessedTime -= frametime;

                if (EngineState.isShouldStop())
                    stop();

                if (frameCounter >= NANOSECOND) {
                    window.setTitle("%s | FPS: %s".formatted(EngineSettings.getWindowTitle(), frames));
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (shouldRender) {
                update();
                render();

                frames++;
            }
        }

        window.hide();
        cleanup();
    }

    private void processInput() {
    }

    private void update() {
        window.update();
    }

    private void render() {
        window.render();
    }

    private void cleanup() {
        window.cleanup();
    }
}
