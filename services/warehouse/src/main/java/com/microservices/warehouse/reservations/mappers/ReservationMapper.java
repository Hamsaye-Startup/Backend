package com.microservices.warehouse.reservations.mappers;

import com.microservices.warehouse.kafka.requests.ReservationNotifyRequest;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import org.springframework.stereotype.Service;

/**
 * Mapper for converting {@link ReservationNotifyRequest} to {@link ReservationEntity}.
 *
 * <p>
 * This service provides a method for mapping reservation notification requests to reservation entities.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class ReservationMapper {

    /**
     * Converts a {@link ReservationNotifyRequest} to a {@link ReservationEntity}.
     *
     * @param notification the reservation notification request to be converted
     * @return the corresponding reservation entity
     * @since 1.0
     */
    public ReservationEntity toReservation(ReservationNotifyRequest notification) {
        return ReservationEntity.builder()
                .reservationId(notification.reservationId())
                .reservedBy(notification.reservedBy())
                .fromDate(notification.fromDate())
                .toDate(notification.toDate())
                .build();
    }
}
