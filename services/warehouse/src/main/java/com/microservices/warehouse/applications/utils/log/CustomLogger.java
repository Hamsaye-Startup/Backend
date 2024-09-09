package com.microservices.warehouse.applications.utils.log;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * A singleton logger class for custom logging of messages at various levels.
 * <p>
 * This class provides methods to log messages with different severity levels: INFO, DEBUG, WARN, and ERROR.
 * It ensures that logs are timestamped and printed to the console with appropriate formatting.
 * </p>
 * <p>
 * The logger follows the Singleton design pattern to ensure that only one instance of the logger exists throughout
 * the application. This instance is thread-safe due to double-checked locking in the {@link #getInstance()} method.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
public class CustomLogger {

    private static CustomLogger instance;
    private static final Object lock = new Object();

    private CustomLogger() {}

    /**
     * Retrieves the singleton instance of the {@link CustomLogger}.
     * <p>
     * This method ensures that only one instance of the logger is created and provides thread-safe access to it.
     * </p>
     *
     * @return the singleton instance of {@link CustomLogger}
     */
    public static CustomLogger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CustomLogger();
                }
            }
        }
        return instance;
    }

    /**
     * Logs an informational message.
     *
     * @param message the message to log
     */
    public void info(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "INFO", message));
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log
     */
    public void debug(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "DEBUG", message));
    }

    /**
     * Logs a warning message.
     *
     * @param message the message to log
     */
    public void warn(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "WARN", message));
    }

    /**
     * Logs a warning message with an optional cause.
     *
     * @param message the message to log
     * @param cause   the cause of the warning, which may be {@code null}
     */
    public void warn(String message, Throwable cause) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "WARN", message));
        if (cause != null) {
            System.out.println(String.format("cause: %s", cause.getMessage()));
        }
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log
     */
    public void error(String message) {
        System.err.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "ERROR", message));
    }

    /**
     * Logs an error message with an optional cause.
     *
     * @param message the message to log
     * @param cause   the cause of the error, which may be {@code null}
     */
    public void error(String message, Throwable cause) {
        System.err.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "ERROR", message));
        if (cause != null) {
            System.err.println(String.format("cause: %s", cause.getMessage()));
        }
    }
}
