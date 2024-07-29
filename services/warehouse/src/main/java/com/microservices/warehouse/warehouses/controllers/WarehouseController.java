package com.microservices.warehouse.warehouses.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.warehouses.requests.NewWarehouseRequest;
import com.microservices.warehouse.warehouses.requests.WarehouseRequest;
import com.microservices.warehouse.warehouses.responses.LimitedWarehouseResponse;
import com.microservices.warehouse.warehouses.responses.WarehouseResponse;
import com.microservices.warehouse.warehouses.services.WarehouseServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/storage")
@RequiredArgsConstructor
public class WarehouseController {

    private final MessageMapper mapper;
    private final WarehouseServiceManagement management;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody NewWarehouseRequest warehouse) {
        WarehouseResponse response = management.add(warehouse);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody WarehouseRequest warehouse) {
        WarehouseResponse response = management.update(warehouse);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        LimitedWarehouseResponse response = management.delete(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping
    public ResponseEntity<?> showAllWarehouses(@RequestParam(value = "offset", required = false) LocalDateTime offset) {
        List<LimitedWarehouseResponse> responses = management.findAllWarehouses(offset);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> showWarehouseById(@PathVariable("id") Long id) {
        WarehouseResponse response = management.findById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
