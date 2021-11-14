package org.openengine.core.event;

public abstract class Event {
    private final Long timestamp = System.nanoTime();

    /**
     * Nanosecond of the event creation.
     */
    public final Long getTimestamp() {
        return timestamp;
    }

    /**
     * Indicates that the event should the processed in a separeted thread.
     */
    public abstract boolean isParallel();
}
