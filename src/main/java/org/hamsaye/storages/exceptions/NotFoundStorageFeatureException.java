package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class NotFoundStorageFeatureException extends StorageFeatureException {

    @Serial
    private static final long serialVersionUID = 7962710183389028007L;

    private static final String message = "Storage Category doesn't found";

    public NotFoundStorageFeatureException() {
        super(message);
    }

    public NotFoundStorageFeatureException(String code) {
        super(message + code);
    }
}
