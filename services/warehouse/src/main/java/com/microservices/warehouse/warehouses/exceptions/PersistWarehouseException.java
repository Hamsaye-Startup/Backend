package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class PersistWarehouseException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_WAREHOUSE.message();

    public PersistWarehouseException(String input) {
        super(message, input);
    }

    public PersistWarehouseException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
