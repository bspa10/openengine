package org.openengine.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ClassUtils {

    public static boolean isAbstract(Class<?> klass) {
        return Modifier.isAbstract(klass.getModifiers());
    }

    public static boolean isPublic(Class<?> klass) {
        return Modifier.isPublic(klass.getModifiers());
    }

    public static boolean isLocal(Class<?> klass) {
        return klass.getTypeName().startsWith("org.openengine");
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> klass, Object ... parameters) {
        if (klass.getEnclosingClass() != null)
            throw new ReflectionException("Oh no!! InnerClass?!");

        for (var constructor: klass.getDeclaredConstructors())
            if (constructor.getParameterCount() == parameters.length)
                try {
                    ConstructorUtils.makeAccessible(constructor);
                    return (T) constructor.newInstance(parameters);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new ReflectionException("Unable to create [%s] instance".formatted(klass.getTypeName()), e);
                }

        throw new ReflectionException("Constructor for [%s] not found".formatted(klass.getCanonicalName()));
    }

    public static List<Method> getMethods(Object object, Class<? extends Annotation> annotation) {
        return getMethods(object.getClass(), annotation);
    }

    public static List<Method> getMethods(Class<?> klass, Class<? extends Annotation> annotation) {
        return Arrays
                .stream(klass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
}
