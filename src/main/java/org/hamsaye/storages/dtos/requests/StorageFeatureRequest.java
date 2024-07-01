package org.hamsaye.storages.dtos.requests;

import lombok.Builder;

@Builder
public record StorageFeatureRequest(
        String title,
        String description,
        String icon
) {}
