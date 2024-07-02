package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageFeatureMapperException extends StorageFeatureException {

    @Serial
    private static final long serialVersionUID = 7962710183389028010L;

    private static final String message = "storage feature can not be map to object.";

    public StorageFeatureMapperException() {
        super(message);
    }

    public StorageFeatureMapperException(String code) {
        super(message + code);
    }
}
