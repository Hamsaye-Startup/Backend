package com.microservices.warehouse.application.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.warehouse.application.exceptions.*;
import com.microservices.warehouse.application.mapper.MessageMapper;
import com.microservices.warehouse.application.api.response.ExceptionMessage;
import com.microservices.warehouse.application.utils.log.CustomLogger;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerImpl {

    private final MessageMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex, WebRequest request) throws JsonProcessingException {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        ExceptionMessage exception = mapper.toException(getErrorsMap(errors), request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<?> handleException(DataAccessException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(
                new CustomJpaPersistanceException(ex.getMostSpecificCause().getMessage()),
                request.getHeader("X_USER_ID"),
                request.getContextPath());

        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({CustomInternalServerErrorException.class})
    public ResponseEntity<?> handleException(CustomInternalServerErrorException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({AuthenticationCredentialNotFoundException.class})
    public ResponseEntity<?> handleException(AuthenticationCredentialNotFoundException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({CustomJpaPersistanceException.class})
    public ResponseEntity<?> handleException(CustomJpaPersistanceException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({CustomNotFoundException.class})
    public ResponseEntity<?> handleException(CustomNotFoundException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({CustomNotRemovableObjectException.class})
    public ResponseEntity<?> handleException(CustomNotRemovableObjectException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({EmptyFileUploadedException.class})
    public ResponseEntity<?> handleException(EmptyFileUploadedException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({FailedUploadFileException.class})
    public ResponseEntity<?> handleException(FailedUploadFileException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler({IllegalOperationException.class})
    public ResponseEntity<?> handleException(IllegalOperationException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, request.getHeader("X_USER_ID"), request.getContextPath());
        return new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.status()));
    }
}
