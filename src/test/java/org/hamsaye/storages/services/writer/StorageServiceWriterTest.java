package org.hamsaye.storages.services.writer;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceWriterTest {

    @Mock
    private StorageRepository storageRepository;
    private StorageServiceWriter underTest;

    @BeforeEach
    void setUp() {
        underTest = new StorageServiceWriter(storageRepository);
    }

    @AfterEach
    void tearDown() {
        storageRepository.deleteAll();
    }

    @Test
    void canPersist() {
        //given
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

        underTest.persist(storage);

        // when
        ArgumentCaptor<StorageEntity> argumentCaptor =
                ArgumentCaptor.forClass(StorageEntity.class);

        // then
        verify(storageRepository).save(argumentCaptor.capture());

        StorageEntity captorStorage = argumentCaptor.getValue();

        assertThat(captorStorage).isEqualTo(storage);
    }

    @Test
    void persistAndFlush() {
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

        underTest.persistAndFlush(storage);

        // when
        ArgumentCaptor<StorageEntity> argumentCaptor =
                ArgumentCaptor.forClass(StorageEntity.class);

        // then
        verify(storageRepository).saveAndFlush(argumentCaptor.capture());

        StorageEntity captorStorage = argumentCaptor.getValue();

        assertThat(captorStorage).isEqualTo(storage);
    }

    @Test
    @Disabled
    void update() {

    }

    @Test
    @Disabled
    void delete() {
    }
}