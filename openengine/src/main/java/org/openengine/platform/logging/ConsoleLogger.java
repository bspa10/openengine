package org.openengine.platform.logging;

import org.openengine.core.logging.AbstractLogger;
import org.openengine.core.logging.LogLevel;
import org.openengine.core.logging.Logger;
import org.openengine.core.logging.LoggingSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ConsoleLogger extends AbstractLogger implements Logger {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault());

    private ConsoleLogger(){}

    private void log(LogLevel level, String message, String color, Object... args) {
        System.out.printf(
                "%s%s %s %s [%s] - %s%s%n",
                color,
                dtf.format(LocalDateTime.now()),
                level.formatted(),
                loggerName,
                Thread.currentThread().getName(),
                message.formatted(args),
                AnsiColor.RESET
        );
    }

    @Override
    public void fatal(String message, Object... args) {
        log(LogLevel.FATAL, message, AnsiColor.RED, args);
        System.exit(9);
    }

    @Override
    public void error(String message, Object... args) {
        log(LogLevel.ERROR, message, AnsiColor.RED, args);
    }

    @Override
    public void warn(String message, Object... args) {
        if (LoggingSettings.isWarning())
            log(LogLevel.WARNING, message, AnsiColor.YELLOW, args);
    }

    @Override
    public void info(String message, Object... args) {
        if (LoggingSettings.isInfo())
            log(LogLevel.INFO, message, AnsiColor.GREEN, args);
    }

    @Override
    public void debug(String message, Object... args) {
        if (LoggingSettings.isDebug())
            log(LogLevel.DEBUG, message, AnsiColor.BLUE, args);
    }

    @Override
    public void trace(String message, Object... args) {
        if (LoggingSettings.isTrace())
            log(LogLevel.TRACE, message, AnsiColor.CYAN, args);
    }
}
