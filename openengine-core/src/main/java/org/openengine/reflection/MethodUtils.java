package org.openengine.reflection;

import org.openengine.OpenEngineException;

import java.lang.reflect.Method;

public abstract class MethodUtils {
    private MethodUtils(){}

    public static Object invoke(Object target, Method method, Object ... parameters) {
        try {
            method.setAccessible(true);
            return method.invoke(target, parameters);
        } catch (Exception ex) {
            final Throwable cause = ex.getCause();
            if (cause instanceof OpenEngineException) {
                throw (OpenEngineException) cause;
            }

            throw new ReflectionException(ex.getMessage(), ex);
        }
    }

}
