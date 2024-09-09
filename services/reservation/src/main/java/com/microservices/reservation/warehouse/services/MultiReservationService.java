package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.exceptions.NotFoundReservationException;
import com.microservices.reservation.warehouse.exceptions.PersistReservationException;
import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.repositories.MultiReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for managing multiple reservations, including persisting and retrieving reservation data.
 * <p>
 * This class handles the persistence and retrieval of multi-reservation entities. It ensures that operations are executed
 * within a transactional context and manages exceptions related to reservation persistence and retrieval.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MultiReservationService {

    /**
     * Repository for accessing and manipulating multi-reservation entities in the database.
     *
     * @see com.microservices.reservation.warehouse.repositories.MultiReservationRepository
     */
    private final MultiReservationRepository repository;

    /**
     * Persists a multi-reservation entity in the database.
     * <p>
     * This method saves the given reservation entity and flushes changes to the database. If an exception occurs during
     * the persistence operation, a {@link PersistReservationException} is thrown.
     * </p>
     *
     * @param reservationEntity the multi-reservation entity to be persisted
     * @return the persisted multi-reservation entity
     * @throws PersistReservationException if an error occurs during persistence
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public MultiReservationEntity persist(MultiReservationEntity reservationEntity) {
        try {
            return repository.saveAndFlush(reservationEntity);
        } catch (RuntimeException ex) {
            throw new PersistReservationException(ex.getCause(), reservationEntity.getReservedBy().toString());
        }
    }

    /**
     * Finds a multi-reservation entity by its unique identifier.
     * <p>
     * This method retrieves the multi-reservation entity with the specified ID from the database. If the entity is not
     * found, a {@link NotFoundReservationException} is thrown.
     * </p>
     *
     * @param uid the unique identifier of the multi-reservation entity
     * @return the found multi-reservation entity
     * @throws NotFoundReservationException if the entity with the specified ID is not found
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public MultiReservationEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundReservationException(uid.toString()));
    }
}
