package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.log.Functionality;

@Builder
public record StorageFeatureResponse(
        String title,
        String description,
        String icon
) implements Functionality {}
