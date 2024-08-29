package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.models.FeatureEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class FeatureRowMapper implements RowMapper<FeatureEntity> {

    @Override
    public FeatureEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return FeatureEntity.builder()
                .id(resultSet.getLong("feature_id"))
                .code(resultSet.getString("code"))
                .title(resultSet.getString("title"))
                .createdAt(LocalDateTime.parse(resultSet.getString("created_at")))
                .modifiedAt(LocalDateTime.parse(resultSet.getString("modified_at")))
                .desc(resultSet.getString("description"))
                .build();
    }
}
