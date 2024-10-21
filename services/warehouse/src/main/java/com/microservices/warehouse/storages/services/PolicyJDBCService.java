package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.storages.mappers.PolicyRowMapper;
import com.microservices.warehouse.storages.models.PolicyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides operations related to policies via JDBC.
 * It handles CRUD operations and associations of policies with storage entities.
 *
 * <p>This service uses {@link JdbcTemplate} to interact with the database and perform SQL operations
 * related to policies.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PolicyJDBCService {

    /**
     * JdbcTemplate used for executing SQL queries.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Finds a policy by its code.
     *
     * @param code The code of the policy to be found.
     * @return The {@link PolicyEntity} corresponding to the policy code.
     * @throws CustomNotFoundException If no policy with the specified code is found.
     * @since 1.0
     */
    public PolicyEntity findPolicyByCode(String code) {
        String sql = """
                SELECT policy_id, code, title, created_at, modified_at, description
                FROM tb_storage_policy
                WHERE code = ?
                """;
        List<PolicyEntity> policies = jdbcTemplate.query(sql, new PolicyRowMapper(), code);
        return policies.stream().findFirst()
                .orElseThrow(() -> new CustomNotFoundException("policy [" + code + "] is not exist"));
    }

    /**
     * Inserts a new policy into the database.
     *
     * @param policy The {@link PolicyEntity} to be inserted.
     * @return The inserted {@link PolicyEntity}.
     * @since 1.0
     */
    public PolicyEntity insertPolicy(PolicyEntity policy) {
        String sql = """
                INSERT INTO tb_storage_policy(code, title, description)
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql, policy.getCode(), policy.getTitle(), policy.getDesc());
        return policy;
    }

    /**
     * Updates an existing policy in the database.
     *
     * @param existed The existing {@link PolicyEntity} to be updated.
     * @param policy The new {@link PolicyEntity} with updated information.
     * @return The updated {@link PolicyEntity}.
     * @since 1.0
     */
    public PolicyEntity updatePolicy(PolicyEntity existed, PolicyEntity policy) {
        String sql = """
                UPDATE tb_storage_policy
                SET code = ?, title = ?, description = ?
                WHERE policy_id = ?
                """;

        jdbcTemplate.update(sql, policy.getCode(), policy.getTitle(), policy.getDesc(), existed.getId());
        return policy;
    }

    /**
     * Updates the policies associated with a specific storage entity.
     *
     * @param codes The list of policy codes to be associated with the storage.
     * @param storageId The ID of the storage entity.
     * @since 1.0
     */
    public void updateStoragePolicyByCode(List<String> codes, Long storageId) {

        // Delete all the policies that belong to the storage
        deleteStoragePoliciesByStorageId(storageId);

        // Insert new policies for the storage
        insertStoragePolicies(codes, storageId);
    }

    /**
     * Deletes all policies associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @since 1.0
     */
    private void deleteStoragePoliciesByStorageId(Long storageId) {
        String sql = """
                DELETE FROM in_storage_policy
                WHERE fk_storage_id = ?
                """;

        jdbcTemplate.update(sql, storageId);
    }

    /**
     * Inserts policies associated with a specific storage entity.
     *
     * @param codes The list of policy codes to be inserted.
     * @param storageId The ID of the storage entity.
     * @since 1.0
     */
    private void insertStoragePolicies(List<String> codes, Long storageId) {
        String sql = """
                INSERT INTO in_storage_policy(fk_storage_id, fk_policy_id)
                SELECT policy_id, ?
                FROM tb_storage_policy
                WHERE code IN (?);
                """;

        jdbcTemplate.update(sql, storageId, codes);
    }

    /**
     * Deletes a policy by its code.
     *
     * @param code The code of the policy to be deleted.
     * @since 1.0
     */
    public void deletePolicyByCode(String code) {

        // Delete policies from child table
        deleteStoragePoliciesByCode(code);

        // Delete policies from parent table
        String sql = """
                DELETE FROM tb_storage_policy
                WHERE code = ?
                """;

        jdbcTemplate.update(sql, code);
    }

    /**
     * Deletes policies associated with a specific storage entity by policy code.
     *
     * @param code The code of the policy to be deleted from the storage.
     * @since 1.0
     */
    private void deleteStoragePoliciesByCode(String code) {
        String sql = """
                DELETE FROM in_storage_policy
                WHERE fk_policy_id IN (
                    SELECT policy_id FROM tb_storage_policy WHERE code = ?
                );
                """;

        jdbcTemplate.update(sql, code);
    }

    /**
     * Retrieves all policies with pagination support.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link PolicyEntity}.
     * @since 1.0
     */
    public Page<PolicyEntity> findAllPolicies(Pageable pageable) {
        String sql = """
                SELECT policy_id, code, title, created_at, modified_at, description
                FROM tb_storage_policy
                ORDER BY policy_id desc
                LIMIT ?
                OFFSET ?
                """;

        // Get the limit and offset
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        // Fetching the page of policies
        List<PolicyEntity> policies = jdbcTemplate.query(sql, new PolicyRowMapper(), limit, offset);

        // Counting the total number of records
        String countSql = """
                SELECT COUNT(*)
                FROM tb_storage_policy;
                """;

        Long total = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(policies, pageable, total == null ? 0L : total);
    }

    /**
     * Finds all policies associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @return A {@link List} of {@link PolicyEntity} associated with the specified storage.
     * @since 1.0
     */
    public List<PolicyEntity> findPoliciesByStorageId(Long storageId) {
        String sql = """
                SELECT f.policy_id, f.code, f.title, f.created_at, f.modified_at, f.description
                FROM tb_storage_policy f
                JOIN in_storage_policy s ON (s.fk_policy_id = f.policy_id)
                WHERE s.fk_storage_id = ?
                """;

        return jdbcTemplate.query(sql, new PolicyRowMapper(), storageId);
    }
}
