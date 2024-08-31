package com.microservices.warehouse.kafka.consumers;

import com.microservices.warehouse.kafka.requests.ReservationNotifyRequest;
import com.microservices.warehouse.kafka.requests.ReservationNotifyType;
import com.microservices.warehouse.reservations.mappers.ReservationMapper;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationConsumerService {

    private final ReservationMapper reservationMapper;
    private final ReservationService reservationService;

    private final StorageService storageService;

    @KafkaListener(
            id = "storage-reservation-listener-id",
            topics = "storage-reservation",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void reservedStorageListener(
            ReservationNotifyRequest notification,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "storage-reservation-listener-id",
                notification
        );

        if (notification.type().equals(ReservationNotifyType.NEW_RESERVE)) {

            // persist the reservation
            ReservationEntity reservation = reservationMapper.toReservation(notification);
            StorageEntity storage = storageService.findStorageById(notification.warehouseId());
            ReservationEntity inserted = reservationService.insertReservedStorage(reservation, storage);

            log.info("new reservation[{}] consumed by warehouse.", inserted.getReservationId());
        }
    }
}
