package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.models.FeatureEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class implements the RowMapper interface to map rows of a ResultSet to FeatureEntity objects.
 * It is used for converting each row of a SQL query result into a FeatureEntity instance.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Component
public class FeatureRowMapper implements RowMapper<FeatureEntity> {

    /**
     * This method maps a row from the ResultSet to a FeatureEntity object.
     * It retrieves values like feature ID, code, title, description, and timestamps (createdAt, modifiedAt) from the ResultSet.
     *
     * @param resultSet The ResultSet containing the data from the database.
     * @param rowNum The number of the current row in the ResultSet.
     * @return A FeatureEntity object containing the data from the current row of the ResultSet.
     * @throws SQLException If an SQL exception occurs while retrieving data from the ResultSet.
     * @since 1.0
     */
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
