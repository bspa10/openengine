package org.openengine.core;

import org.joml.Math;
import org.joml.Vector2i;
import org.openengine.core.event.Event;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggerFactory;

public abstract class EngineSettings {
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineSettings.class);
    private EngineSettings(){}

    private static int eventThreadCount = 0;

    /**
     * The Number o threads for parallel event processing.
     * <br>
     * <br>
     * default: 0
     */
    public static int getEventThreadCount() {
        return eventThreadCount;
    }

    /**
     * Set the number o threads for parallel event processing.
     * <br>
     * <br>
     * If set to zero (default), event the explicitly parallel event will be serialized.
     * @see Event#isParallel()
     */
    public static void setEventThreadCount(int eventThreadCount) {
        EngineSettings.eventThreadCount = eventThreadCount;
    }


    private static String windowTitle = "Game";
    public static String getWindowTitle() {
        return windowTitle;
    }
    public static void setWindowTitle(String desired) {
        LOGGER.debug("Configuring Window Title [%s]", desired);
        windowTitle = desired;
    }

    private static final Vector2i windowSize = new Vector2i(800, 600);
    public static Vector2i getWindowSize() {
        return windowSize;
    }
    public static void setWindowSize(int width, int height) {
        setWindowSize(new Vector2i(width, height));
    }
    public static void setWindowSize(Vector2i desired) {
        LOGGER.debug("Configuring Window Size [%s, %s]", desired.x, desired.y);
        windowSize.set(desired);
    }

    private static long framerate = 240L;
    public static long getFramerate() {
        return framerate;
    }
    public static void setFramerate(long framerate) {
        LOGGER.debug("Configuring Framerate [%s]", framerate);
        EngineSettings.framerate = framerate;
    }

    private static float fieldOfView = Math.toRadians(60.0f);
    public static float getFieldOfView() {
        return fieldOfView;
    }
    public static void setFieldOfView(float fieldOfView) {
        LOGGER.debug("Configuring Field of View [%s]", fieldOfView);
        EngineSettings.fieldOfView = Math.toRadians(fieldOfView);
    }

    private static float zNear = 0.01f;
    public static float getzNear() {
        return zNear;
    }
    public static void setzNear(float zNear) {
        LOGGER.debug("Configuring ZNEAR [%s]", zNear);
        EngineSettings.zNear = zNear;
    }

    private static float zFar = 1000f;
    public static float getzFar() {
        return zFar;
    }
    public static void setzFar(float zFar) {
        LOGGER.debug("Configuring ZFAR [%s]", zFar);
        EngineSettings.zFar = zFar;
    }

}
