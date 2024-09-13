package com.hamsaye.report.violations.mappers;

import com.hamsaye.report.violations.models.*;
import com.hamsaye.report.violations.requests.ViolationRequest;
import com.hamsaye.report.violations.responses.*;
import com.hamsaye.report.violations.services.ViolationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViolationMapper {

    private final ViolationTypeService violationTypeService;
    private final ViolationTypeMapper violationTypeMapper;

    public ViolationEntity toViolation(
            ViolationRequest violationRequest,
            UUID reporter
    ) {

        // find the violation type by code
        ViolationTypeEntity type = violationTypeService.findViolationTypeByCode(
                violationRequest.type()
        );

        return ViolationEntity.builder()
                .trackingNum(UUID.randomUUID())
                .reporterId(reporter)
                .incidentDate(violationRequest.incidentDate())
                .incidentDesc(violationRequest.incidentDesc())
                .type(type)
                .status(ViolationStatus.NEW)
                .build();
    }

    public WarehouseViolationEntity toWarehouseViolation(
            ViolationRequest violationRequest,
            UUID reporter,
            Long warehouseId
    ) {

        // find the violation type by code
        ViolationTypeEntity type = violationTypeService.findViolationTypeByCode(
                violationRequest.type()
        );

        return WarehouseViolationEntity.violationBuilder()
                .trackingNum(UUID.randomUUID())
                .reporterId(reporter)
                .incidentDate(violationRequest.incidentDate())
                .incidentDesc(violationRequest.incidentDesc())
                .type(type)
                .status(ViolationStatus.NEW)
                .warehouseId(warehouseId)
                .build();
    }

    public UserViolationEntity toUserViolation(
            ViolationRequest violationRequest,
            UUID reporter,
            UUID userId
    ) {

        // find the violation type by code
        ViolationTypeEntity type = violationTypeService.findViolationTypeByCode(
                violationRequest.type()
        );

        return UserViolationEntity.violationBuilder()
                .trackingNum(UUID.randomUUID())
                .reporterId(reporter)
                .incidentDate(violationRequest.incidentDate())
                .incidentDesc(violationRequest.incidentDesc())
                .type(type)
                .status(ViolationStatus.NEW)
                .userId(userId)
                .build();
    }

    public ViolationResponse toResponse(ViolationEntity violation) {
        return ViolationResponse.builder()
                .track(violation.getTrackingNum())
                .createdAt(violation.getCreatedAt())
                .incidentDate(violation.getIncidentDate())
                .incidentDesc(violation.getIncidentDesc())
                .reporterId(violation.getReporterId())
                .status(violation.getStatus())
                .type(violationTypeMapper.toResponse(violation.getType()))
                .ref(toReference(violation))
                .build();
    }

    private ViolationReference toReference(ViolationEntity violation) {
        if (violation instanceof WarehouseViolationEntity) {
            return WarehouseReference.builder()
                    .id(((WarehouseViolationEntity) violation).getWarehouseId())
                    .build();
        }
        else if (violation instanceof UserViolationEntity) {
            return UserReference.builder()
                    .id(((UserViolationEntity) violation).getUserId())
                    .build();
        }
        else {
            return null;
        }
    }
}
