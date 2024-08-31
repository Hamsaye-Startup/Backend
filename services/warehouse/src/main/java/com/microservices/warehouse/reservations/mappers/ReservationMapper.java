package com.microservices.warehouse.reservations.mappers;

import com.microservices.warehouse.kafka.requests.ReservationNotifyRequest;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public ReservationEntity toReservation(ReservationNotifyRequest notification) {
        return ReservationEntity.builder()
                .reservationId(notification.reservationId())
                .reservedBy(notification.reservedBy())
                .fromDate(notification.fromDate())
                .toDate(notification.toDate())
                .build();
    }
}
