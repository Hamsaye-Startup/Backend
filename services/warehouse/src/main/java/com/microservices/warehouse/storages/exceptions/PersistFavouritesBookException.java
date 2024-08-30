package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class PersistFavouritesBookException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_FAVOURITES_BOOK.message();

    public PersistFavouritesBookException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public PersistFavouritesBookException(String input) {
        super(message, input);
    }
}
