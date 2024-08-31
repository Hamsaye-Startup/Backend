package com.microservices.reservation.applications.handler;

import com.microservices.reservation.applications.exceptions.ExpiredTokenException;
import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.applications.messages.ExceptionMessage;
import com.microservices.reservation.applications.responses.ResponseMessageType;
import com.microservices.reservation.applications.utils.log.CustomLogger;
import com.microservices.reservation.installments.exceptions.NotFoundInstallmentException;
import com.microservices.reservation.installments.exceptions.PersistInstallmentException;
import com.microservices.reservation.products.exceptions.NotFoundProductTypeException;
import com.microservices.reservation.products.exceptions.PersistProductException;
import com.microservices.reservation.transactions.exceptions.NotFoundTransactionException;
import com.microservices.reservation.transactions.exceptions.PersistTransactionException;
import com.microservices.reservation.warehouse.exceptions.ImpossibleTotalInstallmentsException;
import com.microservices.reservation.warehouse.exceptions.NotFoundReservationException;
import com.microservices.reservation.warehouse.exceptions.PersistReservationException;
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

    @ExceptionHandler({ExpiredTokenException.class})
    public ResponseEntity<?> getExpiredTokenException(ExpiredTokenException ex, WebRequest request) {
        // generate a log
        logger.info(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.EXPIRED_TOKEN.code());
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ImpossibleTotalInstallmentsException.class})
    public ResponseEntity<?> getImpossibleTotalInstallmentsException(ImpossibleTotalInstallmentsException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.TOTAL_INSTALLMENT.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundReservationException.class})
    public ResponseEntity<?> getNotFoundReservationException(NotFoundReservationException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.RESERVATION_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistReservationException.class})
    public ResponseEntity<?> getPersistReservationException(PersistReservationException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_RESERVATION.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundTransactionException.class})
    public ResponseEntity<?> getNotFoundTransactionException(NotFoundTransactionException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.TRANSACTION_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistTransactionException.class})
    public ResponseEntity<?> getPersistTransactionException(PersistTransactionException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_TRANSACTION.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundProductTypeException.class})
    public ResponseEntity<?> getNotFoundProductTypeException(NotFoundProductTypeException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PRODUCT_TYPE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistProductException.class})
    public ResponseEntity<?> getPersistProductException(PersistProductException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_PRODUCT.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundInstallmentException.class})
    public ResponseEntity<?> getNotFoundInstallmentException(NotFoundInstallmentException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.INSTALLMENT_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistInstallmentException.class})
    public ResponseEntity<?> getPersistInstallmentException(PersistInstallmentException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_INSTALLMENT.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
