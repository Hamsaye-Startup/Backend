package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.WarehouseMapper;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.requests.NewWarehouseRequest;
import com.microservices.warehouse.warehouses.requests.WarehouseRequest;
import com.microservices.warehouse.warehouses.responses.LimitedWarehouseResponse;
import com.microservices.warehouse.warehouses.responses.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceManagement {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseService warehouseService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WarehouseResponse add(NewWarehouseRequest warehouse, String token) {
        // generate a warehouse entity
        WarehouseEntity warehouseEntity = warehouseMapper.toWarehouseEntity(warehouse, token);

        // TODO: check the warehouse privacy and policies

        // TODO: initial the warehouse status
        warehouseEntity.setStatus("TEST");

        return warehouseMapper.toResponse(warehouseService.persist(warehouseEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WarehouseResponse update(WarehouseRequest warehouse) {
        // find the warehouse
        WarehouseEntity warehouseEntity = warehouseService.findById(warehouse.id());

        // generate a warehouse entity
        WarehouseEntity newWarehouseEntity = warehouseMapper.toWarehouseEntity(warehouse, warehouseEntity);

        // TODO: check the warehouse privacy and policies

        // TODO: initial the warehouse status

        return warehouseMapper.toResponse(warehouseService.persist(newWarehouseEntity));
    }

    public LimitedWarehouseResponse delete(Long id) {
        // find the warehouse
        WarehouseEntity warehouseEntity = warehouseService.findById(id);
        return warehouseMapper.toLimitResponse(warehouseService.delete(warehouseEntity));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LimitedWarehouseResponse> findAllWarehouses(LocalDateTime offset) {
        // check the null pointer exception
        List<WarehouseEntity> warehouses;
        if (offset == null) {
            warehouses = warehouseService.findAllWarehouses();
        } else {
            warehouses = warehouseService.findAllWarehouses(offset);
        }
        return warehouses.stream()
                .map(warehouseMapper::toLimitResponse)
                .collect(Collectors.toList());
    }

    public WarehouseResponse findById(Long id) {
        return warehouseMapper.toResponse(warehouseService.findById(id));
    }
}
