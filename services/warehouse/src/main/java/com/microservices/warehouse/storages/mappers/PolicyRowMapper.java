package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.models.PolicyEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class implements the RowMapper interface to map rows of a ResultSet to PolicyEntity objects.
 * It is used to convert each row of a SQL query result into a PolicyEntity instance.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Component
public class PolicyRowMapper implements RowMapper<PolicyEntity> {

    /**
     * This method maps a row from the ResultSet to a PolicyEntity object.
     * It retrieves values such as the policy ID, code, title, description, and timestamps (createdAt, modifiedAt) from the ResultSet.
     *
     * @param resultSet The ResultSet containing data retrieved from the database.
     * @param rowNum The number of the current row in the ResultSet.
     * @return A PolicyEntity object containing the data from the current row of the ResultSet.
     * @throws SQLException If an SQL exception occurs while retrieving data from the ResultSet.
     * @since 1.0
     */
    @Override
    public PolicyEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return PolicyEntity.builder()
                .id(resultSet.getLong("feature_id"))
                .code(resultSet.getString("code"))
                .title(resultSet.getString("title"))
                .createdAt(LocalDateTime.parse(resultSet.getString("created_at")))
                .modifiedAt(LocalDateTime.parse(resultSet.getString("modified_at")))
                .desc(resultSet.getString("description"))
                .build();
    }
}
