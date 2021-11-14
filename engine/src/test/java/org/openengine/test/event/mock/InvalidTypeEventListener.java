package org.openengine.test.event.mock;

import org.openengine.engine.event.annotation.Listener;

public class InvalidTypeEventListener {

    @Listener
    public void foo(String string) {

    }

}
