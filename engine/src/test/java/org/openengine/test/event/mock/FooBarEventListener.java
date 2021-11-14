package org.openengine.test.event.mock;

import org.openengine.engine.event.annotation.Listener;

public class FooBarEventListener {

    @Listener
    public void foo(FooEvent event) {
        System.out.println(Thread.currentThread().getName());
    }

    @Listener
    public void bar(BarEvent event) {
        System.out.println(Thread.currentThread().getName());
    }

}
