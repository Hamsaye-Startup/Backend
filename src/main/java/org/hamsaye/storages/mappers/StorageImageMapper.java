package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageImageRequest;
import org.hamsaye.storages.dtos.responses.StorageImageResponse;
import org.hamsaye.storages.models.StorageImageEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageImageMapper {

    public StorageImageResponse storageImageToStorageImageResponse(StorageImageEntity storageImage) {

        // convert second to seventh image to array
        String[] images = {
                storageImage.getSecondImage(),
                storageImage.getThirdImage(),
                storageImage.getForthImage(),
                storageImage.getFifthImage(),
                storageImage.getSixthImage(),
                storageImage.getSeventhImage()
        };

        return StorageImageResponse.builder()
                .cover(storageImage.getCoverImage())
                .images(images)
                .build();
    }

    public StorageImageEntity storageImageRequestToStorageImage(StorageImageRequest request) {
        return StorageImageEntity.builder()
                .coverImage(request.cover())
                .secondImage(request.images()[0])
                .thirdImage(request.images()[1])
                .forthImage(request.images()[2])
                .fifthImage(request.images()[3])
                .sixthImage(request.images()[4])
                .seventhImage(request.images()[5])
                .build();
    }
}
