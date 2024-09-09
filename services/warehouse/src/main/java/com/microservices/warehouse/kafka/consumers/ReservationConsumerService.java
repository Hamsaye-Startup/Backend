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

/**
 * Service for consuming Kafka messages related to storage reservations.
 * <p>
 * This service listens for reservation notifications from Kafka topics and processes
 * them to persist reservations in the storage system.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationConsumerService {

    /**
     * See {@link com.microservices.warehouse.reservations.mappers.ReservationMapper} for more details.
     */
    private final ReservationMapper reservationMapper;

    /**
     * See {@link com.microservices.warehouse.reservations.services.ReservationService} for more details.
     */
    private final ReservationService reservationService;

    /**
     * See {@link com.microservices.warehouse.storages.services.StorageService} for more details.
     */
    private final StorageService storageService;

    /**
     * Listener method for processing reservation notifications from Kafka.
     *
     * <p>
     * This method listens to the "storage-reservation" topic and handles messages of type
     * {@link ReservationNotifyType}. It maps the notification to a
     * {@link ReservationEntity} and persists it in the storage system.
     * </p>
     *
     * @param notification the reservation notification message
     * @param key the Kafka message key, can be null
     * @since 1.0
     */
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
