package org.openengine.test.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openengine.engine.event.EventBus;
import org.openengine.engine.event.EventBusException;
import org.openengine.test.event.mock.*;

@Tag("Unit")
public class EventBusTestUnit {

    @Nested
    class Subscribe {

        @Test
        void whenSubscribeNullThenShouldThrowException(){
            Assertions.assertEquals("Listener class is null",
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.subscribe(null)
                    ).getMessage()
            );
        }

        @Test
        void whenSubscribeWithNoEventListeningMethodThenShouldThrowException() {
            Assertions.assertEquals("Listener class [%s] has no @Listener annotated method".formatted(String.class.getTypeName()),
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.subscribe(String.class)
                    ).getMessage()
            );
        }

        @Test
        void whenSubscribeListenerWithInvalidParameterTypeThenShouldThrowException(){
            Assertions.assertEquals("Listener method [%s.foo(...)] parameter must be the event type".formatted(InvalidTypeEventListener.class.getTypeName()),
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.subscribe(InvalidTypeEventListener.class)
                    ).getMessage()
            );
        }

        @Test
        void whenSubscribeListenerWithInvalidParameterCountThenShouldThrowException(){
            Assertions.assertEquals("Listener method [%s.foo(...)] must have only one parameter, the event".formatted(InvalidTypeCountEventListener.class.getTypeName()),
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.subscribe(InvalidTypeCountEventListener.class)
                    ).getMessage()
            );
        }

        @Test
        void whenSubscribeAValidListenerThenShouldBeOkay() {
            EventBus.subscribe(FooBarEventListener.class);
        }
    }

    @Nested
    class Post {

        @Test
        void whenSubmitNullEventThenShouldThrowException() {
            Assertions.assertEquals("Event is null",
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.post(null)
                    ).getMessage()
            );
        }

        @Test
        void whenSubmitUnListenerEventThenShouldThrowException() {
            Assertions.assertEquals("No listener for [%s]".formatted(UnListenedEvent.class.getTypeName()),
                    Assertions.assertThrows(EventBusException.class,
                            () -> EventBus.post(new UnListenedEvent())
                    ).getMessage()
            );
        }

        @Test
        void whenSubmitFooEventThenShouldBeOkay() {
            EventBus.subscribe(FooBarEventListener.class);
            EventBus.post(new FooEvent());
        }

    }

}
