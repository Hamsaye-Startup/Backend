package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.storages.models.FeatureEntity;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FeatureJDBCServiceTest {
    
    @InjectMocks
    private FeatureJDBCService underTest;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_insert_feature_successful() {
        // given
        // generate the feature
        String code = "AE12G";
        FeatureEntity feature = FeatureEntity.builder()
                .code(code)
                .title("Title for test feature")
                .storages(new HashSet<>())
                .build();

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when
        FeatureEntity result = underTest.insertFeature(feature);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(feature, result);
    }

    @Test
    void checking_finding_feature_by_code_successful() {
        // given
        // generate the feature
        String code = "AE12G";
        FeatureEntity feature = FeatureEntity.builder()
                .code(code)
                .title("Title for test feature")
                .storages(new HashSet<>())
                .build();

        // mock
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(feature));

        // when
        FeatureEntity result = underTest.findFeatureByCode(code);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(feature, result);
    }

    @Test
    void checking_finding_feature_by_code_unsuccessful_throws_exception() {
        // given
        String code = "AE12G";

        // mock
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenThrow(CustomNotFoundException.class);

        // then & when
        // assertion
        assertThrows(CustomNotFoundException.class, () -> underTest.findFeatureByCode(code));
    }

    @Test
    void should_update_feature_successful() {
        // given
        // generate the feature
        FeatureEntity exist = FeatureEntity.builder()
                .code("AE12G")
                .title("Title for exist feature")
                .storages(new HashSet<>())
                .build();

        FeatureEntity feature = FeatureEntity.builder()
                .code("AE12G")
                .title("Title for test feature")
                .storages(new HashSet<>())
                .build();


        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when
        FeatureEntity result = underTest.updateFeature(exist, feature);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(feature, result);
        assertNotEquals(exist, result);
    }

    @Test
    void should_update_feature_by_code_successful() {
        // given
        // generate the codes
        Long storageId = 1001L;
        List<String> codes = Arrays.asList("AE12G", "ERG31", "BV7O0");

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when & then
        // assertion
        assertDoesNotThrow(() -> underTest.updateStorageFeatureByCode(codes, storageId));
    }

    @Test
    void should_delete_feature_by_code_successful() {
        // given
        // generate the codes
        String code = "AE12G";

        // mock
        Mockito.when(jdbcTemplate.update(anyString(), any(String.class)))
                .thenReturn(1);

        // when & then
        // assertion
        assertDoesNotThrow(() -> underTest.deleteFeatureByCode(code));
    }

    @Test
    void check_finding_feature_page_successful() {
        // given
        // generate the feature
        String code = "AE12G";
        FeatureEntity feature = FeatureEntity.builder()
                .code(code)
                .title("Title for test feature")
                .storages(new HashSet<>())
                .build();

        PageImpl<FeatureEntity> policies = new PageImpl<>(Collections.singletonList(feature));

        String query =
                """
                SELECT feature_id, code, title, created_at, modified_at, description
                FROM tb_storage_feature
                ORDER BY feature_id desc
                LIMIT ?
                OFFSET ?
                """;

        // mock
        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(feature));
        Mockito.when(jdbcTemplate.queryForObject(anyString(), eq(Long.class)))
                .thenReturn(1L);

        // when
        Page<FeatureEntity> results = underTest.findAllFeatures(PageRequest.of(0, 10));

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(policies.getContent(), results.getContent());
    }

    @Test
    void check_finding_feature_list_by_storage_id_successful() {
        // given
        // generate the feature
        Long storageId = 1001L;
        FeatureEntity feature = FeatureEntity.builder()
                .code("AE12G")
                .title("Title for test feature")
                .storages(new HashSet<>())
                .build();

        List<FeatureEntity> policies = Collections.singletonList(feature);

        String query = """
                SELECT f.feature_id, f.code, f.title, f.created_at, f.modified_at, f.description
                FROM tb_storage_feature f
                JOIN in_storage_feature s ON (s.fk_feature_id = f.feature_id)
                WHERE s.fk_storage_id = ?
                """;

        // mock
        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class), anyLong()))
                .thenReturn(Collections.singletonList(feature));

        // when
        List<FeatureEntity> results = underTest.findFeaturesByStorageId(storageId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(policies.size(), results.size());
        assertEquals(policies, results);
        verify(jdbcTemplate, times(1)).query(eq(query), any(RowMapper.class), anyLong());
    }
}