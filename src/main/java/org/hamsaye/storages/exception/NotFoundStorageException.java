package org.hamsaye.storages.exception;

public class NotFoundStorageException extends RuntimeException {
    private static final String message = "Storage doesn't found";

    public NotFoundStorageException() {
        super(message);
    }

    public NotFoundStorageException(String code) {
        super(message + " ,code: " + code);
    }
}
