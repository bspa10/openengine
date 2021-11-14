package org.openengine.cdi;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provider {

    /**
     * Indicates that this provider can be overwritten.
     * <p>
     *  <br>
     *  default: true
     * </p>
     */
    boolean overridable() default true;

    /**
     * Should create only one instance.
     * <br>
     * <br>
     * default: true
     */
    boolean singleton() default true;
}
