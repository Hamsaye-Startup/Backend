package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.exceptions.NotFoundReservationException;
import com.microservices.reservation.warehouse.models.ConfirmReserveEnum;
import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.models.ReservationStats;
import com.microservices.reservation.warehouse.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for managing reservations, including finding, confirming, and deleting reservations.
 * <p>
 * This class provides methods to find reservations based on different criteria, confirm or reject reservations, and
 * handle persistence operations related to reservations.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    /**
     * Repository for performing CRUD operations on reservation entities.
     *
     * @see com.microservices.reservation.warehouse.repositories.ReservationRepository
     */
    private final ReservationRepository repository;

    /**
     * Finds reservations by the owner's UUID.
     * <p>
     * This method returns a paginated list of reservations where the specified UUID is the owner.
     * </p>
     *
     * @param uid the UUID of the owner
     * @param pageable pagination information
     * @return a paginated list of reservation entities
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByOwner(UUID uid, Pageable pageable) {
        return repository.findByOwnerId(uid, pageable);
    }

    /**
     * Finds reservations by the renter's UUID.
     * <p>
     * This method returns a paginated list of reservations where the specified UUID is the renter.
     * </p>
     *
     * @param uid the UUID of the renter
     * @param pageable pagination information
     * @return a paginated list of reservation entities
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByRenter(UUID uid, Pageable pageable) {
        return repository.findByRenterId(uid, pageable);
    }

    /**
     * Finds reservations by the warehouse's ID.
     * <p>
     * This method returns a paginated list of reservations for the specified warehouse ID.
     * </p>
     *
     * @param id the ID of the warehouse
     * @param pageable pagination information
     * @return a paginated list of reservation entities
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByWarehouse(Long id, Pageable pageable) {
        return repository.findByWarehouse(id, pageable);
    }

    /**
     * Finds a reservation by its unique identifier.
     * <p>
     * This method returns the reservation entity for the specified UUID or throws a {@link NotFoundReservationException}
     * if the reservation does not exist.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return the reservation entity
     * @throws NotFoundReservationException if the reservation with the specified UUID is not found
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ReservationEntity findReservationById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundReservationException(uid.toString()));
    }

    /**
     * Confirms or rejects a reservation.
     * <p>
     * This method updates the reservation status based on the confirmation flag. If confirmed, the reservation status
     * is set to {@link ConfirmReserveEnum} and saved to the repository. If not confirmed, the reservation
     * is deleted from the repository.
     * </p>
     *
     * @param reservation the reservation entity to be confirmed or rejected
     * @param confirmed true if the reservation should be confirmed, false if it should be rejected
     * @return the updated reservation entity
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ReservationEntity confirmReservation(ReservationEntity reservation, boolean confirmed) {

        // get the reservation stats
        ReservationStats stats = reservation.getStats();
        if (confirmed) {
            stats.setConfirmed(ConfirmReserveEnum.CONFIRMED);
            reservation.setStats(stats);
            repository.save(reservation);
        } else {
            repository.deleteById(reservation.getUid());
        }
        return reservation;
    }
}
