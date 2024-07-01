package org.hamsaye.storages.services.reader;

import org.hamsaye.storages.daos.StorageFeatureRepository;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.storages.services.reader.StorageFeatureServiceReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageFeatureServiceReaderTest {

    @Mock
    private StorageFeatureRepository featureRepository;
    private StorageFeatureServiceReader underTest;

    @BeforeEach
    void setUp() {
        underTest = new StorageFeatureServiceReader(featureRepository);
    }

    @AfterEach
    void tearDown() {
        featureRepository.deleteAll();
    }

    @Test
    void findByTitle() {
        // given
        StorageFeatureEntity feature = StorageFeatureEntity.builder()
                .title("feature title sample 1")
                .icon("feature icon's path sample 1")
                .hasIcon(true)
                .description("feature description sample 1")
                .build();

        // mock
        when(featureRepository.selectByTitle(feature.getTitle())).thenReturn(Optional.of(feature));

        // when
        StorageFeatureEntity byTitle = underTest.findByTitle(feature.getTitle());

        // then
        verify(featureRepository, times(1)).selectByTitle(feature.getTitle());
        assertEquals(byTitle, feature, "features should be equals");
    }

    @Test
    void findAll() {
        // given
        StorageFeatureEntity feature = StorageFeatureEntity.builder()
                .title("feature title sample 1")
                .icon("feature icon's path sample 1")
                .hasIcon(true)
                .description("feature description sample 1")
                .build();

        // mock
        when(featureRepository.findAll()).thenReturn(List.of(feature));

        // when
        List<StorageFeatureEntity> featureList = underTest.findAll();

        // then
        verify(featureRepository, times(1)).findAll();
        assertEquals(1, featureList.size());
        assertEquals(featureList, List.of(feature), "all features should be equal in list");
    }
}