package org.hamsaye.utils.log;

public class Logger {

    private static Logger instance;
    private static final Object lock = new Object();
    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Logger(); // Create instance if not already created
                }
            }
        }
        return instance;
    }

    public void log(LogLevel level, String message) {
        // Implement your logic here to log the message based on level
        System.out.println(String.format("[%s] %s", level, message)); // Example logging to console
    }
}
