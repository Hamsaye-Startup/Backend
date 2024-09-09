package com.microservices.user.application.exceptions;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class CustomRuntimeException extends RuntimeException {

    private final String message;
    private Throwable cause;
    private final String input;

    public CustomRuntimeException(
            String message,
            String input) {

        this.message = message;
        this.input = input;
    }

    public CustomRuntimeException(
            String message,
            Throwable cause,
            String input) {

        this.message = message;
        this.cause = cause;
        this.input = input;
    }

    @Override
    public String getMessage() {
        return "message: " + this.message + " ," + "input: " + this.input;
    }

    @Override
    public synchronized Throwable getCause() {
        return this.cause;
    }
}
