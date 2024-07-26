package com.microservices.warehouse.warehouses.repositories;

import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {

    @Query("select w from WarehouseEntity w order by w.createdAt desc limit 20")
    <S extends WarehouseEntity> List<S> findAllWarehouses();

    @Query("select w from WarehouseEntity w where w.createdAt > :offsetTime order by w.createdAt desc limit 20")
    <S extends WarehouseEntity> List<S> findAllWarehouses(@Param("offsetTime") LocalDateTime offset);
}
