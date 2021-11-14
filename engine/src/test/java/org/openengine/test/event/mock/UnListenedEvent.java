package org.openengine.test.event.mock;

import org.openengine.engine.event.Event;

public class UnListenedEvent extends Event {
    @Override
    public boolean isParallel() {
        return false;
    }
}
