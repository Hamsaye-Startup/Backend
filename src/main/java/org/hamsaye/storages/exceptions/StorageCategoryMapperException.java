package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageCategoryMapperException extends StorageCategoryException {

    @Serial
    private static final long serialVersionUID = 7962710183389028009L;

    private static final String message = "storage category can not be map to object.";

    public StorageCategoryMapperException() {
        super(message);
    }

    public StorageCategoryMapperException(String code) {
        super(message + code);
    }
}
