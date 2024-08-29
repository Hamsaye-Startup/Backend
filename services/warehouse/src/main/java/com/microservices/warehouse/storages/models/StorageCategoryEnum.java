package com.microservices.warehouse.storages.models;

import lombok.Getter;

@Getter
public enum StorageCategoryEnum {

    BUSINESS("BUSINESS");

    private final String name;
    StorageCategoryEnum(String name) {
        this.name = name;
    }
}
