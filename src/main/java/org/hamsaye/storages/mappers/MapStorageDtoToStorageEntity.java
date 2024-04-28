package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.StorageDto;
import org.hamsaye.storages.models.StorageEntity;
import org.springframework.core.convert.converter.Converter;

@jakarta.persistence.Converter
public class MapStorageDtoToStorageEntity implements Converter<StorageDto, StorageEntity> {

    @Override
    public StorageEntity convert(StorageDto source) {
        return StorageEntity.builder()
                .width(source.width())
                .height(source.height())
                .maxWeight(source.maxWeight())
                .amount(source.amount())
                .discountAmount(source.discountAmount())
                .description(source.description())
                .build();
    }
}
