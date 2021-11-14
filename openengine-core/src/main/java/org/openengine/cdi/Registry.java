package org.openengine.cdi;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class Registry {
    private Registry() {}

    private static final ConcurrentMap<Class<?>, Module> modules = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, Method> providers = new ConcurrentHashMap<>();

    public static void add(Module module) {
        for (var provider : module.getProviders()) {
            final var returnType = provider.getReturnType();
            final var annotation = provider.getAnnotation(Provider.class);

            if (providers.containsKey(returnType) && ! annotation.overridable())
                throw new CDIException("Module [%s] does not allow override of provider [%s %s()]".formatted(
                        modules.get(returnType).getName(), returnType.getTypeName(), providers.get(returnType).getName()
                ));

            modules.put(returnType, module);
            providers.put(returnType, provider);
        }
    }

    public static Optional<Module> getModule(Class<?> target) {
        if (!modules.containsKey(target))
            return Optional.empty();

        return Optional.of(modules.get(target));
    }

    public static Optional<Method> getProvider(Class<?> target) {
        if (!providers.containsKey(target))
            return Optional.empty();

        return Optional.of(providers.get(target));
    }

    public static void reset() {
        modules.clear();
        providers.clear();
    }
}
