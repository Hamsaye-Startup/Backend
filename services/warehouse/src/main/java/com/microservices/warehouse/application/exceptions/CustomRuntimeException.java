package com.microservices.warehouse.application.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Getter
@Setter
public class CustomRuntimeException extends RuntimeException {

    private final String key;

    public CustomRuntimeException(String message, String key) {
        super(message);
        this.key = key;
    }
}
