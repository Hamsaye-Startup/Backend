package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.log.Functionality;

@Builder
public record StorageCategoryResponse(
        String code,
        String name,
        StorageCategoryResponse parent
) implements Functionality {}
