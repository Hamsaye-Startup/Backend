package org.hamsaye.storages.dtos.requests;

import lombok.Builder;

@Builder
public record StorageCategoryRequest(
        String code,
        String name,
        StorageCategoryRequest parent
) {}
