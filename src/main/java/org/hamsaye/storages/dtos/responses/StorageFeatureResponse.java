package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.functional.Functionality;

import java.util.UUID;

@Builder
public record StorageFeatureResponse(
        UUID uid,
        String title,
        String description,
        String icon
) implements Functionality {}
