package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.exceptions.NotFoundReservationException;
import com.microservices.reservation.warehouse.exceptions.PersistReservationException;
import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import com.microservices.reservation.warehouse.repositories.SingleReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for managing single reservation operations, including persisting and retrieving reservation entities.
 * <p>
 * This class provides methods to persist and retrieve single reservation data using the underlying repository.
 * It handles transaction management and exception handling for reservation persistence and retrieval.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SingleReservationService {

    /**
     * Repository for performing CRUD operations on single reservation entities.
     *
     * @see com.microservices.reservation.warehouse.repositories.SingleReservationRepository
     */
    private final SingleReservationRepository repository;

    /**
     * Persists a single reservation entity to the database.
     * <p>
     * This method saves the provided reservation entity and flushes the changes to the database. If an exception
     * occurs during the persistence operation, a {@link PersistReservationException} is thrown.
     * </p>
     *
     * @param reservationEntity the reservation entity to be persisted
     * @return the persisted reservation entity
     * @throws PersistReservationException if an error occurs while saving the reservation entity
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public SingleReservationEntity persist(SingleReservationEntity reservationEntity) {
        try {
            return repository.saveAndFlush(reservationEntity);
        } catch (RuntimeException ex) {
            throw new PersistReservationException(ex.getCause(), reservationEntity.getReservedBy().toString());
        }
    }

    /**
     * Retrieves a single reservation entity by its unique identifier.
     * <p>
     * This method finds the reservation entity with the specified ID. If no reservation is found, a
     * {@link NotFoundReservationException} is thrown.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return the reservation entity with the specified ID
     * @throws NotFoundReservationException if no reservation with the specified ID is found
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public SingleReservationEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundReservationException(uid.toString()));
    }
}
