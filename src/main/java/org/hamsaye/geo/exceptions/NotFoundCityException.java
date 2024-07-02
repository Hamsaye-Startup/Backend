package org.hamsaye.geo.exceptions;

import java.io.Serial;

public class NotFoundCityException extends CityException {

    @Serial
    private static final long serialVersionUID = 7962710183389028016L;

    private static final String message = "City doesn't found";

    public NotFoundCityException() {
        super(message);
    }

    public NotFoundCityException(String code) {
        super(message + code);
    }
}
