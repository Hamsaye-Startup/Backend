package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.applications.responses.ResponseMessageType;
import com.microservices.warehouse.warehouses.exceptions.NotFoundWarehouseException;
import com.microservices.warehouse.warehouses.exceptions.PersistWarehouseException;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.repositories.WarehouseRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public WarehouseEntity persist(WarehouseEntity warehouseEntity) {
        try {
            return repository.saveAndFlush(warehouseEntity);
        } catch (RuntimeException ex) {
            throw new PersistWarehouseException(ex.getCause(), warehouseEntity.getOwner().toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public WarehouseEntity delete(WarehouseEntity warehouseEntity) {
        repository.delete(warehouseEntity);
        return warehouseEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public WarehouseEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundWarehouseException(id.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<WarehouseEntity> findAllWarehouses() {
        try {
            return repository.findAllWarehouses();
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<WarehouseEntity> findAllWarehouses(LocalDateTime offset) {
        try {
            return repository.findAllWarehouses(offset);
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }
}
