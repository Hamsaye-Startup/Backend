package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.functional.Functionality;

import java.util.UUID;

@Builder
public record StorageCategoryResponse(
        UUID uid,
        String code,
        String name,
        StorageCategoryResponse parent
) implements Functionality {}
