package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.storages.mappers.FeatureRowMapper;
import com.microservices.warehouse.storages.models.FeatureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling feature-related operations using JDBC.
 * It provides methods for performing CRUD operations and managing features in the storage.
 *
 * <p>This service interacts directly with the database using JDBC.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FeatureJDBCService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Finds a feature by its code.
     *
     * @param code The code of the feature to be found.
     * @return The {@link FeatureEntity} associated with the given code.
     * @throws CustomNotFoundException If no feature with the specified code is found.
     * @since 1.0
     */
    public FeatureEntity findFeatureByCode(String code) {
        String sql = """
                SELECT feature_id, code, title, created_at, modified_at, description
                FROM tb_storage_feature
                WHERE code = ?
                """;
        List<FeatureEntity> features = jdbcTemplate.query(sql, new FeatureRowMapper(), code);
        return features.stream().findFirst()
                .orElseThrow(() -> new CustomNotFoundException("feature [" + code + "] is not exist"));
    }

    /**
     * Inserts a new feature into the database.
     *
     * @param feature The {@link FeatureEntity} to be inserted.
     * @return The inserted {@link FeatureEntity}.
     * @since 1.0
     */
    public FeatureEntity insertFeature(FeatureEntity feature) {
        String sql = """
                INSERT INTO tb_storage_feature(code, title, description)
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql, feature.getCode(), feature.getTitle(), feature.getDesc());
        return feature;
    }

    /**
     * Updates an existing feature in the database.
     *
     * @param existed The existing {@link FeatureEntity} to be updated.
     * @param feature The new {@link FeatureEntity} information.
     * @return The updated {@link FeatureEntity}.
     * @since 1.0
     */
    public FeatureEntity updateFeature(FeatureEntity existed, FeatureEntity feature) {
        String sql = """
                UPDATE tb_storage_feature
                SET code = ?, title = ?, description = ?
                WHERE feature_id = ?
                """;

        jdbcTemplate.update(sql, feature.getCode(), feature.getTitle(), feature.getDesc(), existed.getId());
        return feature;
    }

    /**
     * Updates the features associated with a specific storage entity.
     *
     * @param codes The list of feature codes to be updated.
     * @param storageId The ID of the storage entity to which the features are associated.
     * @since 1.0
     */
    public void updateStorageFeatureByCode(List<String> codes, Long storageId) {

        // Delete existing features associated with the storage
        deleteStorageFeaturesByStorageId(storageId);

        // Insert new features for the storage
        insertStorageFeatures(codes, storageId);
    }

    /**
     * Deletes all features associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity whose features are to be deleted.
     * @since 1.0
     */
    private void deleteStorageFeaturesByStorageId(Long storageId) {
        String sql = """
                DELETE FROM in_storage_feature
                WHERE fk_storage_id = ?
                """;

        jdbcTemplate.update(sql, storageId);
    }

    /**
     * Inserts new features associated with a specific storage entity.
     *
     * @param codes The list of feature codes to be inserted.
     * @param storageId The ID of the storage entity to which the features will be associated.
     * @since 1.0
     */
    private void insertStorageFeatures(List<String> codes, Long storageId) {
        String sql = """
                INSERT INTO in_storage_feature(fk_storage_id, fk_feature_id)
                SELECT feature_id, ?
                FROM tb_storage_feature
                WHERE code IN (?);
                """;

        jdbcTemplate.update(sql, storageId, codes);
    }

    /**
     * Deletes a feature by its code.
     *
     * @param code The code of the feature to be deleted.
     * @since 1.0
     */
    public void deleteFeatureByCode(String code) {

        // Delete features from the association table
        deleteStorageFeaturesByCode(code);

        // Delete the feature from the main table
        String sql = """
                DELETE FROM tb_storage_feature
                WHERE code = ?
                """;

        jdbcTemplate.update(sql, code);
    }

    /**
     * Deletes features associated with a specific feature code from the association table.
     *
     * @param code The code of the feature whose associations are to be deleted.
     * @since 1.0
     */
    private void deleteStorageFeaturesByCode(String code) {
        String sql = """
                DELETE FROM in_storage_feature
                WHERE fk_feature_id IN (
                    SELECT feature_id FROM tb_storage_feature WHERE code = ?
                );
                """;

        jdbcTemplate.update(sql, code);
    }

    /**
     * Retrieves all features from the database with pagination support.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link FeatureEntity}.
     * @since 1.0
     */
    public Page<FeatureEntity> findAllFeatures(Pageable pageable) {
        String sql = """
                SELECT feature_id, code, title, created_at, modified_at, description
                FROM tb_storage_feature
                ORDER BY feature_id desc
                LIMIT ?
                OFFSET ?
                """;

        // Get the limit and offset from the pageable object
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        // Fetch the page of features
        List<FeatureEntity> features = jdbcTemplate.query(sql, new FeatureRowMapper(), limit, offset);

        // Count the total number of records
        String countSql = """
                SELECT COUNT(*)
                FROM tb_storage_feature;
                """;

        Long total = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(features, pageable, total == null ? 0L : total);
    }

    /**
     * Retrieves all features associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @return A {@link List} of {@link FeatureEntity} associated with the specified storage.
     * @since 1.0
     */
    public List<FeatureEntity> findFeaturesByStorageId(Long storageId) {
        String sql = """
                SELECT f.feature_id, f.code, f.title, f.created_at, f.modified_at, f.description
                FROM tb_storage_feature f
                JOIN in_storage_feature s ON (s.fk_feature_id = f.feature_id)
                WHERE s.fk_storage_id = ?
                """;

        return jdbcTemplate.query(sql, new FeatureRowMapper(), storageId);
    }
}
