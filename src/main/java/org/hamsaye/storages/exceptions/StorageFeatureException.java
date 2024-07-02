package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageFeatureException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 7962710183389028006L;

    public StorageFeatureException(String message) {
        super(message);
    }
}
