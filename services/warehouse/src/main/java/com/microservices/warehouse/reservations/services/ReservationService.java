package com.microservices.warehouse.reservations.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.reservations.repositories.ReservationRepository;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing reservations in the warehouse system.
 * Provides functionality to interact with reservations including retrieving,
 * creating, and searching reservations based on various criteria.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Retrieves a list of all reservations within a specified category and time range.
     * The results are sorted by the 'createdAt' feature in descending order.
     *
     * @param category the category of the storage
     * @param from the start date of the reservation period
     * @param to the end date of the reservation period
     * @return a list of reservations matching the specified criteria
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ReservationEntity> findAllReservationsByCategoryAndReservedTime(
            StorageCategoryEnum category,
            LocalDate from,
            LocalDate to
    ) {
        return reservationRepository.findAllByCategoryAndReservedTime(
                category,
                from,
                to
        );
    }

    /**
     * Finds a reservation by its unique identifier.
     *
     * @param reservationId the UUID of the reservation to retrieve
     * @return the reservation entity associated with the given ID
     * @throws CustomNotFoundException if no reservation is found with the specified ID
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ReservationEntity findReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomNotFoundException("reservation [" + reservationId + "] is not exist"));
    }

    /**
     * Inserts a new reservation for a specified storage entity.
     *
     * @param reservation the reservation entity to be saved
     * @param storage the storage entity associated with the reservation
     * @return the saved reservation entity
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ReservationEntity insertReservedStorage(ReservationEntity reservation, StorageEntity storage) {
        reservation.setStorage(storage);
        return reservationRepository.save(reservation);
    }
}
