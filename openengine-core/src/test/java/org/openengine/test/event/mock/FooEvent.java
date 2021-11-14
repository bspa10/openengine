package org.openengine.test.event.mock;

import org.openengine.core.event.Event;

public class FooEvent extends Event {

    @Override
    public boolean isParallel() {
        return false;
    }

}
