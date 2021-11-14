package org.openengine.engine;

import org.openengine.engine.event.Event;

public abstract class EngineSettings {
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
}
