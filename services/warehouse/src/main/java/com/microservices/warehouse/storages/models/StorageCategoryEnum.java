package com.microservices.warehouse.storages.models;

import lombok.Getter;

/**
 * This enum represents the category type for a storage. It includes different categories that can be assigned to a storage.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

@Getter
public enum StorageCategoryEnum {

    BUSINESS("BUSINESS");

    private final String name;
    StorageCategoryEnum(String name) {
        this.name = name;
    }
}
