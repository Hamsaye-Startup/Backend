package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageAlreadyExistException extends StorageException {

    @Serial
    private static final long serialVersionUID = 7962710183389028002L;
    private static final String message = "Storage already exists in server";

    public StorageAlreadyExistException() {
        super(message);
    }

    public StorageAlreadyExistException(String code) {
        super(message + code);
    }
}
