package com.microservices.warehouse.warehouses.models;

import lombok.Getter;

@Getter
public enum WarehouseStatus {

    TEST("TEST");

    private final String value;

    WarehouseStatus(String value) {
        this.value = value;
    }
}
