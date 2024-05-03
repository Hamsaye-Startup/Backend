package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageCategoryEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageCategoryRepositoryTest {

    @Autowired
    private StorageCategoryRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void selectByName() {
        // given
        StorageCategoryEntity category = StorageCategoryEntity.builder()
                .name("category's name sample 1")
                .code("CSNM1")
                .build();

        underTest.saveAndFlush(category);

        // when
        Optional<StorageCategoryEntity> byName =
                underTest.selectByName(category.getName());

        // then
        assertEquals(1, byName.stream().toList().size());
        assertEquals(byName.get(), category, "categories should be equal");

    }
}