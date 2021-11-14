package org.openengine.test.event.mock;

import org.openengine.engine.event.Event;

public class BarEvent extends Event {

    @Override
    public boolean isParallel() {
        return true;
    }

}
