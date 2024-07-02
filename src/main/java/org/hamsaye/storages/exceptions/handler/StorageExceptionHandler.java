package org.hamsaye.storages.exceptions.handler;

import org.hamsaye.storages.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class StorageExceptionHandler {


    /*
    * TODO generate a clear message formation for exceptions
    * */

    @ExceptionHandler(value = {StorageAlreadyExistException.class, NotFoundStorageException.class})
    protected ResponseEntity<?> handleStorage(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    protected ResponseEntity<?> handleNullPointerException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            StorageMapperException.class, StorageImageMapperException.class,
            StorageFeatureMapperException.class, StorageCategoryMapperException.class
    })
    protected ResponseEntity<?> handleObjectMapperException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
