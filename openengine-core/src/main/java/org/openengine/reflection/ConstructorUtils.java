package org.openengine.reflection;

import java.lang.reflect.Constructor;

public abstract class ConstructorUtils {

    private ConstructorUtils(){}


    public static void makeAccessible(Constructor<?> constructor) {
        constructor.setAccessible(true);
    }
}

