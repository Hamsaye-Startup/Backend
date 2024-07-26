package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundWarehouseException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.WAREHOUSE_NOT_FOUND.message();

    public NotFoundWarehouseException(String input) {
        super(message, input);
    }

    public NotFoundWarehouseException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
