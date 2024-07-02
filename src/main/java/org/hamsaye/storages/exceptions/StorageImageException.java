package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageImageException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 7962710183389028011L;

    public StorageImageException(String message) {
        super(message);
    }
}
