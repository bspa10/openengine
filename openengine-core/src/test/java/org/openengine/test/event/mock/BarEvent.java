package org.openengine.test.event.mock;

import org.openengine.core.event.Event;

public class BarEvent extends Event {

    @Override
    public boolean isParallel() {
        return true;
    }

}
