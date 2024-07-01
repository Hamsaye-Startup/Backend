package org.hamsaye.storages.dtos.requests;

import lombok.Builder;

@Builder
public record StorageImageRequest(
        String cover,
        String[] images
) {}
