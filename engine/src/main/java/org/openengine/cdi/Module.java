package org.openengine.cdi;

import org.openengine.reflection.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

final class Module {
    private final String name;
    private final Object module;
    private final List<Method> providers = new ArrayList<>();

    public Module(Class<?> klass) {
        name = klass.getTypeName();
        module = ClassUtils.newInstance(klass);
    }

    public String getName() {
        return name;
    }
    public Object getInstance() {
        return module;
    }
    public void addProvider(Method method) {
        providers.add(method);
    }
    public List<Method> getProviders() {
        return providers;
    }

}
