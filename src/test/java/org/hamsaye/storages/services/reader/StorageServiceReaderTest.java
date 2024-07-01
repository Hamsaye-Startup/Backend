package org.hamsaye.storages.services.reader;

import org.hamsaye.storages.daos.StorageRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.storages.services.reader.StorageServiceReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceReaderTest {

    @Mock
    private StorageRepository storageRepository;
    private StorageServiceReader underTest;

    @BeforeEach
    void setUp() {
        underTest = new StorageServiceReader(storageRepository);
    }

    @AfterEach
    void tearDown() {
        storageRepository.deleteAll();
    }

    @Test
    void findByName() {
        // given
        StorageFeatureEntity featureEntity = StorageFeatureEntity.builder()
                .title("title's sample")
                .icon("this is our icon path")
                .description("this is simple description")
                .hasIcon(true)
                .build();

        StorageCategoryEntity categoryEntity = StorageCategoryEntity.builder()
                .name("category's name")
                .code("12B")
                .build();

        StorageEntity storage = StorageEntity.builder()
                .name("storage's name")
                .width(12)
                .height(12)
                .amount(440000d)
                .discountAmount(5d)
                .status("200043412b")
                .category(categoryEntity)
                .features(List.of(featureEntity))
                .build();

        // mock
        when(storageRepository.selectByName(storage.getName())).thenReturn(Optional.of(storage));

        // when
        StorageEntity foundedStorage = underTest.findByName(storage.getName());

        // then
        assertNotNull(foundedStorage, "Storage should not be null");
        verify(storageRepository, times(1)).selectByName(storage.getName());
        assertEquals(storage, foundedStorage, "Storage's names should be equal");
    }

    @Test
    void findAll() {
        // given
        StorageFeatureEntity featureEntity = StorageFeatureEntity.builder()
                .title("title's sample")
                .icon("this is our icon path")
                .description("this is simple description")
                .hasIcon(true)
                .build();

        StorageCategoryEntity categoryEntity = StorageCategoryEntity.builder()
                .name("category's name")
                .code("12B")
                .build();

        StorageEntity storage = StorageEntity.builder()
                .name("storage's name")
                .width(12)
                .height(12)
                .amount(440000d)
                .discountAmount(5d)
                .status("200043412b")
                .category(categoryEntity)
                .features(List.of(featureEntity))
                .build();

        // mock
        when(storageRepository.findAll()).thenReturn(List.of(storage));

        // when
        List<StorageEntity> storageList = underTest.findAll();

        // then
        assertEquals(1, storageList.size());
        verify(storageRepository, times(1)).findAll();
    }
}