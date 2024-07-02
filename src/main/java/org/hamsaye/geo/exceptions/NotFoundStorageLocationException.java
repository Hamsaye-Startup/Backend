package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class NotFoundStorageLocationException extends StorageLocationException {

    @Serial
    private static final long serialVersionUID = 7962710183389028017L;

    private static final String message = "Storage Location doesn't found";

    public NotFoundStorageLocationException() {
        super(message);
    }

    public NotFoundStorageLocationException(String code) {
        super(message + code);
    }
}
