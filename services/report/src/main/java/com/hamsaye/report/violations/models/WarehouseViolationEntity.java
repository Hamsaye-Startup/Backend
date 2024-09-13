package com.hamsaye.report.violations.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("warehouse")
public class WarehouseViolationEntity extends ViolationEntity {

    @Builder(builderMethodName = "violationBuilder")
    public WarehouseViolationEntity(
            Long id, UUID trackingNum,
            UUID reporterId,
            Date incidentDate,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            String incidentDesc,
            ViolationTypeEntity type,
            ViolationStatus status,
            Long warehouseId
    ) {
        super(id, trackingNum, reporterId, incidentDate, createdAt, modifiedAt, incidentDesc, type, status);
        this.warehouseId = warehouseId;
    }

    @Column(name = "warehouse_id")
    private Long warehouseId;
}
