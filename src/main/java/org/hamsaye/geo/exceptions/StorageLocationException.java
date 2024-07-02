package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class StorageLocationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7962710183389028015L;

    public StorageLocationException(String message) {
        super(message);
    }
}
