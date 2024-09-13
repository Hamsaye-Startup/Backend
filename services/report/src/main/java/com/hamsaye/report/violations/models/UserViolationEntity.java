package com.hamsaye.report.violations.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("user")
public class UserViolationEntity extends ViolationEntity {

    @Builder(builderMethodName = "violationBuilder")
    public UserViolationEntity(
            Long id, UUID trackingNum,
            UUID reporterId,
            Date incidentDate,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            String incidentDesc,
            ViolationTypeEntity type,
            ViolationStatus status,
            UUID userId
    ) {
        super(id, trackingNum, reporterId, incidentDate, createdAt, modifiedAt, incidentDesc, type, status);
        this.userId = userId;
    }

    @Column(name = "user_id")
    private UUID userId;
}
