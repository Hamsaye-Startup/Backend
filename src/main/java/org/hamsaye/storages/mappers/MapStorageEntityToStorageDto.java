package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.StorageDto;
import org.hamsaye.storages.models.StorageEntity;
import org.springframework.core.convert.converter.Converter;

@jakarta.persistence.Converter
public class MapStorageEntityToStorageDto implements Converter<StorageEntity, StorageDto> {

    @Override
    public StorageDto convert(StorageEntity source) {
        return StorageDto.builder()
                .width(source.getWidth())
                .height(source.getHeight())
                .maxWeight(source.getMaxWeight())
                .amount(source.getAmount())
                .discountAmount(source.getDiscountAmount())
                .description(source.getDescription())
                .build();
    }
}