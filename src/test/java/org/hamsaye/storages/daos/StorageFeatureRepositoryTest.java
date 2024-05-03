package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageFeatureEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageFeatureRepositoryTest {

    @Autowired
    private StorageFeatureRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void selectByTitle() {
        // given
        StorageFeatureEntity feature = StorageFeatureEntity.builder()
                .title("feature title sample 1")
                .icon("feature icon's path sample 1")
                .hasIcon(true)
                .description("feature description sample 1")
                .build();

        underTest.saveAndFlush(feature);

        // when
        Optional<StorageFeatureEntity> byName = underTest.selectByTitle(feature.getTitle());

        // then
        assertEquals(1, byName.stream().toList().size());
        assertEquals(byName.get(), feature, "Features should be equal");
    }
}