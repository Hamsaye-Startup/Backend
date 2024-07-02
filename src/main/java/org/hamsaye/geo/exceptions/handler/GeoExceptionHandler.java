package org.hamsaye.geo.exceptions.handler;

import org.hamsaye.geo.exceptions.CityMapperException;
import org.hamsaye.geo.exceptions.StorageLocationMapperException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GeoExceptionHandler {

    @ExceptionHandler(value = {StorageLocationMapperException.class, CityMapperException.class})
    public ResponseEntity<?> handleObjectMapper(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
