package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageRepositoryTest {

    @Autowired
    private StorageRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void selectByName() {
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

        repository.saveAndFlush(storage);

        Optional<StorageEntity> byName = repository.selectByName(storage.getName());

        // when
        boolean exists = repository.exists(Example.of(byName.get()));

        // then
        assertThat(exists).isTrue();
    }
}