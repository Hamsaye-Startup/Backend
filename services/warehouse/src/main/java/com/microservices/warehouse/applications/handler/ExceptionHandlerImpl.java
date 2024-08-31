package com.microservices.warehouse.applications.handler;

import com.microservices.warehouse.applications.exceptions.NotFoundScopeException;
import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.applications.messages.ExceptionMessage;
import com.microservices.warehouse.applications.responses.ResponseMessageType;
import com.microservices.warehouse.applications.utils.log.CustomLogger;
import com.microservices.warehouse.storages.exceptions.*;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerImpl {

    private final MessageMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<?> getInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.INTERNAL.code());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundScopeException.class})
    public ResponseEntity<?> getNotFoundScopeException(NotFoundScopeException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.SCOPE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundStorageException.class})
    public ResponseEntity<?> getNotFoundWarehouseException(NotFoundStorageException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.STORAGE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundCommentException.class})
    public ResponseEntity<?> getNotFoundReviewException(NotFoundCommentException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.COMMENT_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundReservationException.class})
    public ResponseEntity<?> getNotFoundReservationException(NotFoundReservationException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.RESERVATION_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundPolicyException.class})
    public ResponseEntity<?> getNotFoundPolicyException(NotFoundPolicyException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.POLICY_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundFeatureException.class})
    public ResponseEntity<?> getNotFoundFeatureException(NotFoundFeatureException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.FEATURE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundBookmarkException.class})
    public ResponseEntity<?> getNotFoundBookmarkException(NotFoundBookmarkException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.BOOKMARK_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistStorageException.class})
    public ResponseEntity<?> getPersistWarehouseException(PersistStorageException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_STORAGE.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PersistFeatureException.class})
    public ResponseEntity<?> getPersistFeatureException(PersistFeatureException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_FEATURE.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
