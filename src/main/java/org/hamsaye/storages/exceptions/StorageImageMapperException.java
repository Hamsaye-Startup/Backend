package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageImageMapperException extends StorageImageException {

    @Serial
    private static final long serialVersionUID = 7962710183389028012L;

    private static final String message = "storage can not be map to object.";

    public StorageImageMapperException() {
        super(message);
    }

    public StorageImageMapperException(String code) {
        super(message + code);
    }
}
