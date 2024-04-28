package org.hamsaye.storages.dtos;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record StorageImageDto(
        String coverImage,
        String secondImage,
        String thirdImage,
        String forthImage,
        String fifthImage,
        String sixthImage,
        String seventhImage

) implements Serializable {
}
