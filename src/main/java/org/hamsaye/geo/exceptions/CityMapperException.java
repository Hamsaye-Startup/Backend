package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class CityMapperException extends CityException {

    @Serial
    private static final long serialVersionUID = 7962710183389028018L;

    private static final String message = "city can not be map to object.";

    public CityMapperException() {
        super(message);
    }

    public CityMapperException(String code) {
        super(message + code);
    }
}
