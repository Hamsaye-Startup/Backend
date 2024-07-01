package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.log.Functionality;

@Builder
public record StorageImageResponse(
        String cover,
        String[] images
) implements Functionality {}
