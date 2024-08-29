package com.microservices.warehouse.reservations.repositories;

import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

    @Query("select r from ReservationEntity r " +
            "where ( " +
            "r.reservedStorage.storage.category = :category and " +
            "r.fromDate <= :fromDate and " +
            "r.toDate >= :toDate " +
            ") order by r.reservedStorage.createdAt desc"
    )
    List<ReservationEntity> findAllByCategoryAndReservedTime(
            @Param("category") StorageCategoryEnum category,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
