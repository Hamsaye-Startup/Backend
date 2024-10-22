package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.storages.models.PolicyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PolicyJDBCServiceTest {

    @InjectMocks
    private PolicyJDBCService underTest;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_insert_policy_successful() {
        // given
        // generate the policy
        String code = "AE12G";
        PolicyEntity policy = PolicyEntity.builder()
                .code(code)
                .title("Title for test policy")
                .storages(new HashSet<>())
                .build();

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when
        PolicyEntity result = underTest.insertPolicy(policy);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(policy, result);
    }

    @Test
    void checking_finding_policy_by_code_successful() {
        // given
        // generate the policy
        String code = "AE12G";
        PolicyEntity policy = PolicyEntity.builder()
                .code(code)
                .title("Title for test policy")
                .storages(new HashSet<>())
                .build();

        // mock
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(policy));

        // when
        PolicyEntity result = underTest.findPolicyByCode(code);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(policy, result);
    }

    @Test
    void checking_finding_policy_by_code_unsuccessful_throws_exception() {
        // given
        String code = "AE12G";

        // mock
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenThrow(CustomNotFoundException.class);

        // then & when
        // assertion
        assertThrows(CustomNotFoundException.class, () -> underTest.findPolicyByCode(code));
    }

    @Test
    void should_update_policy_successful() {
        // given
        // generate the policy
        PolicyEntity exist = PolicyEntity.builder()
                .code("AE12G")
                .title("Title for exist policy")
                .storages(new HashSet<>())
                .build();

        PolicyEntity policy = PolicyEntity.builder()
                .code("AE12G")
                .title("Title for test policy")
                .storages(new HashSet<>())
                .build();


        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when
        PolicyEntity result = underTest.updatePolicy(exist, policy);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(policy, result);
        assertNotEquals(exist, result);
    }

    @Test
    void should_update_policy_by_code_successful() {
        // given
        // generate the codes
        Long storageId = 1001L;
        List<String> codes = Arrays.asList("AE12G", "ERG31", "BV7O0");

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when & then
        // assertion
        assertDoesNotThrow(() -> underTest.updateStoragePolicyByCode(codes, storageId));
    }

    @Test
    void should_delete_policy_by_code_successful() {
        // given
        // generate the codes
        String code = "AE12G";

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when & then
        // assertion
        assertDoesNotThrow(() -> underTest.deletePolicyByCode(code));
    }

    @Test
    void check_finding_policy_page_successful() {
        // given
        // generate the policy
        String code = "AE12G";
        PolicyEntity policy = PolicyEntity.builder()
                .code(code)
                .title("Title for test policy")
                .storages(new HashSet<>())
                .build();

        PageImpl<PolicyEntity> policies = new PageImpl<>(Collections.singletonList(policy));

        String query =
                """
                SELECT policy_id, code, title, created_at, modified_at, description
                FROM tb_storage_policy
                ORDER BY policy_id desc
                LIMIT ?
                OFFSET ?
                """;

        // mock
        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(policy));
        Mockito.when(jdbcTemplate.queryForObject(anyString(), eq(Long.class)))
                .thenReturn(1L);

        // when
        Page<PolicyEntity> results = underTest.findAllPolicies(PageRequest.of(0, 10));

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(policies.getContent(), results.getContent());
    }

    @Test
    void check_finding_policy_list_by_storage_id_successful() {
        // given
        // generate the policy
        Long storageId = 1001L;
        PolicyEntity policy = PolicyEntity.builder()
                .code("AE12G")
                .title("Title for test policy")
                .storages(new HashSet<>())
                .build();

        List<PolicyEntity> policies = Collections.singletonList(policy);

        String query = """
                SELECT f.policy_id, f.code, f.title, f.created_at, f.modified_at, f.description
                FROM tb_storage_policy f
                JOIN in_storage_policy s ON (s.fk_policy_id = f.policy_id)
                WHERE s.fk_storage_id = ?
                """;

        // mock
        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class), anyLong()))
                .thenReturn(Collections.singletonList(policy));

        // when
        List<PolicyEntity> results = underTest.findPoliciesByStorageId(storageId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(policies.size(), results.size());
        assertEquals(policies, results);
        verify(jdbcTemplate, times(1)).query(eq(query), any(RowMapper.class), anyLong());
    }
}