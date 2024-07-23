package com.hamsaye.customer.application.handler;

import com.hamsaye.customer.application.exceptions.NotFoundScopeException;
import com.hamsaye.customer.application.mapper.MessageMapper;
import com.hamsaye.customer.application.messages.ExceptionMessage;
import com.hamsaye.customer.application.responses.ResponseMessageType;
import com.hamsaye.customer.customers.exceptions.NotFoundCustomerException;
import com.hamsaye.customer.customers.exceptions.PersistCustomerException;
import com.hamsaye.customer.utils.log.CustomLogger;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomerExceptionHandler {

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

    @ExceptionHandler({NotFoundCustomerException.class})
    public ResponseEntity<?> handleNotFoundExceptions(NotFoundCustomerException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.INTERNAL.code());
        return new ResponseEntity<>(exception, BAD_REQUEST);
    }

    @ExceptionHandler({PersistCustomerException.class})
    public ResponseEntity<?> getPersistUserException(PersistCustomerException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_CUSTOMER.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
