package com.microservices.warehouse.warehouses.models;

import lombok.Getter;

@Getter
public enum WarehouseStatus {

    TEST("test");

    private final String value;

    WarehouseStatus(String value) {
        this.value = value;
    }
}
