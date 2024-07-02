package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7962710183389028000L;

    public StorageException(String message) {
        super(message);
    }
}
