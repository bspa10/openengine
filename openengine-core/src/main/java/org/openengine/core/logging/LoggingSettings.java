package org.openengine.core.logging;

public abstract class LoggingSettings {
    private LoggingSettings(){}

    private static boolean trace = false;
    public static boolean isTrace() {
        return trace;
    }
    public static void setTrace(boolean desired) {
        if (desired) {
            warning = true;
            info = true;
            debug = true;
            trace = true;
        } else
            trace = false;
    }

    private static boolean debug = false;
    public static boolean isDebug() {
        return debug;
    }
    public static void setDebug(boolean desired) {
        if (desired)
            debug = true;

         else {
            debug = false;
            trace = false;
         }
    }

    private static boolean info = true;
    public static boolean isInfo() {
        return info;
    }
    public static void setInfo(boolean desired) {
        if (desired)
            info = true;

        else {
            info = false;
            debug = false;
            trace = false;
        }
    }

    private static boolean warning = true;
    public static boolean isWarning() {
        return warning;
    }
    public static void setWarning(boolean desired) {
        if (desired)
            warning = true;

        else {
            warning = false;
            info = false;
            debug = false;
            trace = false;
        }
    }

}

