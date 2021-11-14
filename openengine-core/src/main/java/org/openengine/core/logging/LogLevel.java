package org.openengine.core.logging;

public enum LogLevel {
    FATAL("FATAL  "),
    ERROR("ERROR  "),
    WARNING("WARNING"),
    INFO("INFO   "),
    DEBUG("DEBUG  "),
    TRACE("TRACE  ");

    private final String formatted;
    LogLevel(String formatted) {
        this.formatted = formatted;
    }

    public String formatted() {
        return formatted;
    }
}
