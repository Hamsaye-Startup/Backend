package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.NotFoundPolicyException;
import com.microservices.warehouse.storages.mappers.PolicyRowMapper;
import com.microservices.warehouse.storages.models.PolicyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyJDBCService {

    private final JdbcTemplate jdbcTemplate;

    public PolicyEntity findPolicyByCode(String code) {
        String sql = """
                SELECT policy_id, code, title, created_at, modified_at, description
                FROM tb_storage_policy
                WHERE code = ?
                """;
        List<PolicyEntity> policies = jdbcTemplate.query(sql, new PolicyRowMapper(), code);
        return policies.stream().findFirst()
                .orElseThrow(() -> new NotFoundPolicyException(code));
    }

    public PolicyEntity insertPolicy(PolicyEntity policy) {
        String sql = """
                INSERT INTO tb_storage_policy(code, title, description)
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql, policy.getCode(), policy.getTitle(), policy.getDesc());
        return policy;
    }

    public PolicyEntity updatePolicy(PolicyEntity existed, PolicyEntity policy) {
        String sql = """
                UPDATE tb_storage_policy
                SET code = ?, title = ?, description = ?
                WHERE policy_id = ?
                """;

        jdbcTemplate.update(sql, policy.getCode(), policy.getTitle(), policy.getDesc(), existed.getId());
        return policy;
    }

    public void updateStoragePolicyByCode(List<String> codes, Long storageId) {

        // delete all the policies that belongs to storage
        deleteStoragePoliciesByStorageId(storageId);

        // insert new policies for storage
        insertStoragePolicies(codes, storageId);
    }

    private void deleteStoragePoliciesByStorageId(Long storageId) {
        String sql = """
                DELETE FROM in_storage_policy
                WHERE fk_storage_id = ?
                """;

        jdbcTemplate.update(sql, storageId);
    }

    private void insertStoragePolicies(List<String> codes, Long storageId) {
        String sql = """
                INSERT INTO in_storage_policy(fk_storage_id, fk_policy_id)
                SELECT policy_id, ?
                FROM tb_storage_policy
                WHERE code IN (?);
                """;

        jdbcTemplate.update(sql, storageId, codes);
    }

    public void deletePolicyByCode(String code) {

        // delete policies from child table
        deleteStoragePoliciesByCode(code);

        // delete policies from parent table
        String sql = """
                DELETE FROM tb_storage_policy
                WHERE code = ?
                """;

        jdbcTemplate.update(sql, code);
    }

    private void deleteStoragePoliciesByCode(String code) {
        String sql = """
                DELETE FROM in_storage_policy
                WHERE fk_policy_id IN (
                    SELECT policy_id FROM tb_storage_policy WHERE code = ?
                );
                """;

        jdbcTemplate.update(sql, code);
    }

    public Page<PolicyEntity> findAllPolicies(Pageable pageable) {
        String sql = """
                SELECT policy_id, code, title, created_at, modified_at, description
                FROM tb_storage_policy
                ORDER BY policy_id desc
                LIMIT ?
                OFFSET ?
                """;

        // get the limit and offset
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
