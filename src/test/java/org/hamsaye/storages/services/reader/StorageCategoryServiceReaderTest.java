package org.hamsaye.storages.services.reader;

import org.hamsaye.storages.daos.StorageCategoryRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.services.reader.StorageCategoryServiceReader;
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
class StorageCategoryServiceReaderTest {

    @Mock
    private StorageCategoryRepository categoryRepository;
    private StorageCategoryServiceReader underTest;

    @BeforeEach
    void setUp() {
        underTest = new StorageCategoryServiceReader(categoryRepository);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void findByName() {
        // given
        StorageCategoryEntity category = StorageCategoryEntity.builder()
                .name("category's name sample 1")
                .code("CNS1")
                .build();

        // mock
        when(categoryRepository.selectByName(category.getName())).thenReturn(Optional.of(category));

        // when
        StorageCategoryEntity byName = underTest.findByName(category.getName());

        // then
        verify(categoryRepository, times(1)).selectByName(category.getName());
        assertEquals(byName, category, "categories should be equal");

    }

    @Test
    void findAll() {
        // given
        StorageCategoryEntity category = StorageCategoryEntity.builder()
                .name("category's name sample 1")
                .code("CNS1")
                .build();

        // mock
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        // when
        List<StorageCategoryEntity> categoryList = underTest.findAll();

        // then
        verify(categoryRepository, times(1)).findAll();
        assertEquals(1, categoryList.size());
        assertEquals(categoryList, List.of(category), "category's lists should be equal");
    }
}