package org.openengine;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class EngineState {

    private EngineState() {}

    private static final AtomicBoolean running = new AtomicBoolean(false);
    public static boolean isRunning() {
        return running.get();
    }
    public static void setRunning() {
        running.getAndSet(true);
    }
    public static void setStopped() {
        running.getAndSet(false);
    }

    private static final AtomicBoolean shouldStop = new AtomicBoolean(false);
    public static boolean isShouldStop() {
        return shouldStop.get();
    }
    public static void setShouldStop() {
        shouldStop.getAndSet(true);
    }
}
