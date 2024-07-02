package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class CityException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7962710183389028015L;

    public CityException(String message) {
        super(message);
    }
}
