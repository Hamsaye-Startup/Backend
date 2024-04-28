package org.hamsaye.storages.dtos;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record StorageFeatureDto (
        String title,
        String description,
        String icon
) implements Serializable {
}
