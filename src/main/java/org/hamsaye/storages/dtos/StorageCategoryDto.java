package org.hamsaye.storages.dtos;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record StorageCategoryDto(
        StorageCategoryDto parent,
        String name
) implements Serializable {
}
