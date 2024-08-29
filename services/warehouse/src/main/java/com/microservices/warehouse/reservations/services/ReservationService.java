package com.microservices.warehouse.reservations.services;

import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.reservations.repositories.ReservationRepository;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /*
    * find all the reservations based on category, from and to date
    * This function is sort the result by feature 'createdAt' desc
    * */
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
}
