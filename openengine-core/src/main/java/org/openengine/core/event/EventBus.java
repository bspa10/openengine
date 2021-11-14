package org.openengine.core.event;

import org.openengine.core.EngineSettings;
import org.openengine.core.event.annotation.Listener;
import org.openengine.reflection.ClassUtils;
import org.openengine.reflection.MethodUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class EventBus {
    private static final ConcurrentMap<Class<?>, List<Object>> registry = new ConcurrentHashMap<>();
    private static final ExecutorService executor;

    static {
        if (EngineSettings.getEventThreadCount() > 0)
            executor = Executors.newFixedThreadPool(EngineSettings.getEventThreadCount());

        else
            executor = null;
    }

    private EventBus(){}

    public static void subscribe(Class<?> klass) {
        if (klass == null)
            throw new EventBusException("Listener class is null");

        final var found = ClassUtils.getMethods(klass, Listener.class);
        if (found.isEmpty())
            throw new EventBusException("Listener class [%s] has no @Listener annotated method".formatted(klass.getTypeName()));

        synchronized(EventBus.class) {
            final var instance = ClassUtils.newInstance(klass);

            for (var method : found) {
                if (method.getParameterCount() != 1)
                    throw new EventBusException("Listener method [%s.%s(...)] must have only one parameter, the event".formatted(klass.getTypeName(), method.getName()));

                final var eventType = method.getParameterTypes()[0];
                if (! Event.class.isAssignableFrom(eventType))
                    throw new EventBusException("Listener method [%s.%s(...)] parameter must be the event type".formatted(klass.getTypeName(), method.getName()));

                if (!registry.containsKey(eventType))
                    registry.put(eventType, new ArrayList<>());

                registry.get(eventType).add(instance);
            }
        }
    }

    public static <T extends Event> void post(T event) {
        if (event == null)
            throw new EventBusException("Event is null");

        if (! registry.containsKey(event.getClass()))
            throw new EventBusException("No listener for [%s]".formatted(event.getClass().getTypeName()));

        for (var listener : registry.get(event.getClass())) {
            final var found = ClassUtils.getMethods(listener.getClass(), Listener.class);

            for (var method : found) {
                if (method.getParameterTypes()[0] != event.getClass())
                    continue;

                if (!event.isParallel()) {
                    MethodUtils.invoke(listener, method, event);
                    continue;
                }

                if (executor != null) {
                    executor.execute(() -> MethodUtils.invoke(listener, method, event));
                    continue;
                }

                MethodUtils.invoke(listener, method, event);
            }
        }
    }
}
