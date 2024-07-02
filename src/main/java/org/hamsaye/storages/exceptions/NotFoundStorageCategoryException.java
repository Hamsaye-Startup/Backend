package org.hamsaye.storages.exceptions;

import java.io.Serial;

public class NotFoundStorageCategoryException extends StorageCategoryException {

    @Serial
    private static final long serialVersionUID = 7962710183389028003L;

    private static final String message = "Storage Category doesn't found";

    public NotFoundStorageCategoryException() {
        super(message);
    }

    public NotFoundStorageCategoryException(String code) {
        super(message + code);
    }
}
