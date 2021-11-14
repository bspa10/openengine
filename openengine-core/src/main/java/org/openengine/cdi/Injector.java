package org.openengine.cdi;

import org.openengine.reflection.ClassUtils;
import org.openengine.reflection.MethodUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class Injector {

    private Injector(){}

    private static final ConcurrentMap<Class<?>, Object> singletons = new ConcurrentHashMap<>();

    public static void reset() {
        Registry.reset();
    }

    public static void register(Class<?> klass) {
        if (klass == null)
            throw new CDIException("Module is null");

        final var providers = ClassUtils.getMethods(klass, Provider.class);
        if (providers.isEmpty())
            throw new CDIException("No provider annotated method found");

        final var module = new Module(klass);
        providers.forEach(module::addProvider);

        Registry.add(module);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> target) {
        if (singletons.containsKey(target))
            return (T) singletons.get(target);

        final var module = Registry
                .getModule(target)
                .orElseThrow(() -> new CDIException("Unknow target [%s]".formatted(target.getTypeName())));

        final var provider = Registry
                .getProvider(target)
                .orElseThrow(() -> new CDIException("Unknow target [%s]".formatted(target.getTypeName())));

        final var instance = (T) MethodUtils.invoke(module.getInstance(), provider);

        if (provider.getAnnotation(Provider.class).singleton())
            singletons.put(target, instance);

        return instance;
    }

}
