package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class StorageCategoryException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7962710183389028005L;

    public StorageCategoryException(String message) {
        super(message);
    }
}
