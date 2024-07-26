package com.microservices.warehouse.warehouses.models;

import lombok.Getter;

@Getter
public enum CategoryEnum {

    BUSINESS("BUSINESS");

    private final String name;
    CategoryEnum(String name) {
        this.name = name;
    }
}
