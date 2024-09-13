package com.hamsaye.report.violations.services;

import com.hamsaye.report.kafka.producers.DistributedViolationProducerService;
import com.hamsaye.report.kafka.producers.ViolationProducerService;
import com.hamsaye.report.kafka.producers.WarehouseProducerService;
import com.hamsaye.report.kafka.requests.*;
import com.hamsaye.report.violations.mappers.DistributedViolationMapper;
import com.hamsaye.report.violations.mappers.ViolationMapper;
import com.hamsaye.report.violations.mappers.ViolationResultStatusMapper;
import com.hamsaye.report.violations.mappers.ViolationTypeMapper;
import com.hamsaye.report.violations.models.*;
import com.hamsaye.report.violations.requests.ViolationRequest;
import com.hamsaye.report.violations.responses.DistributedViolationResponse;
import com.hamsaye.report.violations.responses.ViolationResponse;
import com.hamsaye.report.violations.responses.ViolationTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.hamsaye.report.kafka.producers.DistributedViolationProducerService.REFER_VIOLATION_AGENT_TOPIC;
import static com.hamsaye.report.kafka.producers.ViolationProducerService.DISTRIBUTE_VIOLATION_TOPIC;
import static com.hamsaye.report.kafka.producers.WarehouseProducerService.WAREHOUSE_VIOLATION_TOPIC;

@Service
@RequiredArgsConstructor
public class ViolationServiceManagement {

    private final ViolationService violationService;
    private final DistributedViolationService distributedService;
    private final ViolationMapper violationMapper;
    private final DistributedViolationMapper distributedMapper;

    private final ViolationTypeService violationTypeService;
    private final ViolationTypeMapper violationTypeMapper;

    private final ViolationDetailService violationDetailService;

    private final ViolationResultStatusMapper violationResultStatusMapper;

    private final ViolationProducerService violationProducer;
    private final DistributedViolationProducerService distributedProducer;
    private final WarehouseProducerService warehouseProducer;

    public UUID registerWarehouseViolation(
            ViolationRequest request,
            Long warehouseId,
            UUID reporterId
    ) {
        WarehouseViolationEntity violation = violationMapper.toWarehouseViolation(
                request,
                reporterId,
                warehouseId
        );
        violationService.insertViolation(violation);

        violationProducer.send(
                ViolationNotifyRequest.builder()
                        .track(violation.getTrackingNum())
                        .message(ViolationNotifyType.SEND_NEW_MESSAGE.getMessage())
                        .type(ViolationNotifyType.SEND_NEW_MESSAGE)
                        .build(),
                DISTRIBUTE_VIOLATION_TOPIC
        );

        warehouseProducer.send(
                WarehouseViolationNotifyRequest.builder()
                        .warehouseId(warehouseId)
                        .message(WarehouseViolationNotifyType.SEND_NEW_MESSAGE.getMessage())
                        .type(WarehouseViolationNotifyType.SEND_NEW_MESSAGE)
                        .build(),
                WAREHOUSE_VIOLATION_TOPIC
        );

        return violation.getTrackingNum();
    }

    public UUID registerUserViolation(
            ViolationRequest request,
            UUID userId,
            UUID reporterId
    ) {
        UserViolationEntity violation = violationMapper.toUserViolation(
                request,
                reporterId,
                userId
        );
        violationService.insertViolation(violation);

        violationProducer.send(
                ViolationNotifyRequest.builder()
                        .track(violation.getTrackingNum())
                        .message(ViolationNotifyType.SEND_NEW_MESSAGE.getMessage())
                        .type(ViolationNotifyType.SEND_NEW_MESSAGE)
                        .build(),
                DISTRIBUTE_VIOLATION_TOPIC
        );

        return violation.getTrackingNum();
    }

    public UUID removeViolation(UUID track) {

        ViolationEntity violation = violationService.findViolationByTrack(track);
        violationService.deleteViolation(violation);
        return track;
    }

    public ViolationResponse findViolationByTrack(UUID track) {
        return violationMapper.toResponse(violationService.findViolationByTrack(track));
    }

    public DistributedViolationResponse findDistributedViolationByTrack(UUID track) {
        ViolationEntity violation = violationService.findViolationByTrack(track);
        DistributedViolationEntity distributed = distributedService.findViolationByTrack(violation);
        return distributedMapper.toResponse(distributed);
    }

    public void addViolationDocument(
            UUID track,
            MultipartFile file
    ) {
        ViolationEntity violation = violationService.findViolationByTrack(track);

        // TODO: 2.store image in a bucket

        violationDetailService.insertViolationDetail(ViolationDetailEntity.builder()
                .violationEntity(violation)
                .documentId("not implemented yet") // TODO: implement it when cloud service is ready
                .build());
    }

    public Page<DistributedViolationResponse> findAllViolationsByAgentId(
            UUID agentId,
            Pageable pageable
    ) {
        // sort by status, priority and createdAt by default
        return distributedService.findViolationByAgentId(agentId, pageable)
                .map(distributedMapper::toResponse);
    }

    public ViolationResponse startViolation(UUID track) {

        ViolationEntity violation = violationService.findViolationByTrack(track);
        DistributedViolationEntity distributed = distributedService.findViolationByTrack(violation);

        distributedService.updateViolationDateTime(
                distributed,
                LocalDateTime.now(),
                true
        );
        violationService.updateViolationStatus(
                violation,
                ViolationStatus.ON_PROCESS
        );
        return violationMapper.toResponse(violation);
    }

    public DistributedViolationResponse changeViolationAgent(
            UUID track,
            UUID agentId
    ) {
        ViolationEntity violation = violationService.findViolationByTrack(track);
        DistributedViolationEntity distributed = distributedService.findViolationByTrack(violation);

        UUID previousAgent = distributed.getAgentId();
        distributedService.updateViolationAgent(
                distributed,
                agentId
        );

        distributedProducer.send(
                DistributedViolationNotifyRequest.builder()
                        .track(violation.getTrackingNum())
                        .detail(ViolationAgentsNotifyDetail.builder()
                                .previousAgent(previousAgent)
                                .currentAgent(agentId)
                                .build())
                        .message(DistributedViolationNotifyType.SEND_NEW_MESSAGE.getMessage())
                        .type(DistributedViolationNotifyType.SEND_NEW_MESSAGE)
                        .build(),
                REFER_VIOLATION_AGENT_TOPIC
        );

        return distributedMapper.toResponse(distributed);
    }

    public DistributedViolationResponse finishViolation(
            UUID track,
            String result
    ) {

        ViolationEntity violation = violationService.findViolationByTrack(track);
        DistributedViolationEntity distributed = distributedService.findViolationByTrack(violation);

        distributedService.updateViolationDateTime(
                distributed,
                LocalDateTime.now(),
                false
        );

        distributedService.updateViolationResult(
                distributed,
                violationResultStatusMapper.convertViolationResultStatus(result)
        );

        violationService.updateViolationStatus(
                violation,
                ViolationStatus.FINISHED
        );

        // TODO: 5. send a message for notify the sender (notification)

        return distributedMapper.toResponse(distributed);
    }

    public List<byte[]> downloadViolationDocument(UUID track) {

        ViolationEntity violation = violationService.findViolationByTrack(track);
        List<ViolationDetailEntity> details = violationDetailService.findViolationDetailsByTrack(
                violation
        );

        // TODO: 2.find the document in bucket by violation detail

        return null;
    }

    public List<ViolationTypeCategory> findAllViolationTypeCategories() {
        return List.of(ViolationTypeCategory.values());
    }

    public List<ViolationTypeResponse> findAllViolationTypesByCategory(String category) {
        return violationTypeService.findViolationTypesByCategory(violationTypeMapper.convertViolationTypeCategory(category))
                .stream()
                .map(violationTypeMapper::toResponse)
                .toList();
    }

    public List<ViolationResultStatus> findAllViolationResultStatus() {
        return List.of(ViolationResultStatus.values());
    }

    // the warehouse's status should be updated when the investigation is finished
    // TODO: write the scheduler for it.
}
