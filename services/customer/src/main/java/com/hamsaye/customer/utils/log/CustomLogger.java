package com.hamsaye.customer.utils.log;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CustomLogger {

    private static CustomLogger instance;
    private static final Object lock = new Object();
    private CustomLogger() {}

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

    public void info(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "INFO", message));
    }
    public void debug(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "DEBUG", message));
    }
    public void warn(String message) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "WARN", message));
    }
    public void warn(String message, Throwable cause) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "WARN", message));
        if (cause != null) {
            System.out.println(String.format("cause: %s", cause.getMessage()));
        }
    }
    public void error(String message) {
        System.err.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "ERROR", message));
    }
    public void error(String message, Throwable cause) {
        System.out.println(String.format("\n%s [%s] : %s", LocalDateTime.now(ZoneId.systemDefault()), "ERROR", message));
        if (cause != null) {
            System.out.println(String.format("cause: %s", cause.getMessage()));
        }
    }
}
