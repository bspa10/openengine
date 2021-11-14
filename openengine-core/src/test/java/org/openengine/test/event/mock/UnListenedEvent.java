package org.openengine.test.event.mock;

import org.openengine.core.event.Event;

public class UnListenedEvent extends Event {
    @Override
    public boolean isParallel() {
        return false;
    }
}
