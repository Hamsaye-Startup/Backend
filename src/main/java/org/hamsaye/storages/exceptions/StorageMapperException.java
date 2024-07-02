package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageMapperException extends StorageException {

    @Serial
    private static final long serialVersionUID = 7962710183389028008L;

    private static final String message = "storage can not be map to object.";

    public StorageMapperException() {
        super(message);
    }

    public StorageMapperException(String code) {
        super(message + code);
    }
}
