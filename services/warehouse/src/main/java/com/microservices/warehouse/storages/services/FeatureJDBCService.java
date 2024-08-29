package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.NotFoundFeatureException;
import com.microservices.warehouse.storages.mappers.FeatureRowMapper;
import com.microservices.warehouse.storages.models.FeatureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureJDBCService {

    private final JdbcTemplate jdbcTemplate;

    public FeatureEntity findFeatureByCode(String code) {
        String sql = """
                SELECT feature_id, code, title, created_at, modified_at, description
                FROM tb_storage_feature
                WHERE code = ?
                """;
        List<FeatureEntity> features = jdbcTemplate.query(sql, new FeatureRowMapper(), code);
        return features.stream().findFirst()
                .orElseThrow(() -> new NotFoundFeatureException(code));
    }

    public FeatureEntity insertFeature(FeatureEntity feature) {
        String sql = """
                INSERT INTO tb_storage_feature(code, title, description)
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql, feature.getCode(), feature.getTitle(), feature.getDesc());
        return feature;
    }

    public FeatureEntity updateFeature(FeatureEntity existed, FeatureEntity feature) {
        String sql = """
                UPDATE tb_storage_feature
                SET code = ?, title = ?, description = ?
                WHERE feature_id = ?
                """;

        jdbcTemplate.update(sql, feature.getCode(), feature.getTitle(), feature.getDesc(), existed.getId());
        return feature;
    }

    public void updateStorageFeatureByCode(List<String> codes, Long storageId) {

        // delete all the features that belongs to storage
        deleteStorageFeaturesByStorageId(storageId);

        // insert new features for storage
        insertStorageFeatures(codes, storageId);
    }

    private void deleteStorageFeaturesByStorageId(Long storageId) {
        String sql = """
                DELETE FROM in_storage_feature
                WHERE fk_storage_id = ?
                """;

        jdbcTemplate.update(sql, storageId);
    }

    private void insertStorageFeatures(List<String> codes, Long storageId) {
        String sql = """
                INSERT INTO in_storage_feature(fk_storage_id, fk_feature_id)
                SELECT feature_id, ?
                FROM tb_storage_feature
                WHERE code IN (?);
                """;

        jdbcTemplate.update(sql, storageId, codes);
    }

    public void deleteFeatureByCode(String code) {

        // delete features from child table
        deleteStorageFeaturesByCode(code);

        // delete features from parent table
        String sql = """
                DELETE FROM tb_storage_feature
                WHERE code = ?
                """;

        jdbcTemplate.update(sql, code);
    }

    private void deleteStorageFeaturesByCode(String code) {
        String sql = """
                DELETE FROM in_storage_feature
                WHERE fk_feature_id IN (
                    SELECT feature_id FROM tb_storage_feature WHERE code = ?
                );
                """;

        jdbcTemplate.update(sql, code);
    }

    public Page<FeatureEntity> findAllFeatures(Pageable pageable) {
        String sql = """
                SELECT feature_id, code, title, created_at, modified_at, description
                FROM tb_storage_feature
                ORDER BY feature_id desc
                LIMIT ?
                OFFSET ?
                """;

        // get the limit and offset
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        // Fetching the page of features
        List<FeatureEntity> features = jdbcTemplate.query(sql, new FeatureRowMapper(), limit, offset);

        // Counting the total number of records
        String countSql = """
                SELECT COUNT(*)
                FROM tb_storage_feature;
                """;

        Long total = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(features, pageable, total == null ? 0L : total);
    }

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
