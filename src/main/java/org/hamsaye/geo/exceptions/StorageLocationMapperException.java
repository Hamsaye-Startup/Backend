package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class StorageLocationMapperException extends StorageLocationException {

    @Serial
    private static final long serialVersionUID = 7962710183389028014L;

    private static final String message = "storage can not be map to object.";

    public StorageLocationMapperException() {
        super(message);
    }

    public StorageLocationMapperException(String code) {
        super(message + code);
    }
}
