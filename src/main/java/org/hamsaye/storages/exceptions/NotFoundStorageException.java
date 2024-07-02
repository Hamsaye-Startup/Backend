package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class NotFoundStorageException extends StorageException {

    @Serial
    private static final long serialVersionUID = 7962710183389028001L;
    private static final String message = "Storage doesn't found";

    public NotFoundStorageException() {
        super(message);
    }

    public NotFoundStorageException(String code) {
        super(message + code);
    }
}
