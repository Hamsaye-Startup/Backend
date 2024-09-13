package com.hamsaye.report.kafka.consumers;

import com.hamsaye.report.kafka.requests.ViolationNotifyRequest;
import com.hamsaye.report.roles.responses.RoleResponse;
import com.hamsaye.report.users.responses.UserDetailResponse;
import com.hamsaye.report.violations.client.RoleClient;
import com.hamsaye.report.violations.client.UserClient;
import com.hamsaye.report.violations.models.DistributedViolationEntity;
import com.hamsaye.report.violations.models.ViolationEntity;
import com.hamsaye.report.violations.services.DistributedViolationService;
import com.hamsaye.report.violations.services.ViolationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViolationConsumerService {

    private final ViolationService violationService;
    private final DistributedViolationService distributedService;

    private final RoleClient roleClient;
    private final UserClient userClient;

    @Value("${application.config.role.support-role-name:role-support}")
    private String roleName;

    @KafkaListener(
            id = "distribute-registered-violation-listener-id",
            topics = "topic-distribute-registered-violation",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void distributeNewViolationsListener(
            ViolationNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        // TODO: 0.generate a log based on message

        ViolationEntity violation = violationService.findViolationByTrack(request.track());

        List<RoleResponse> roles = roleClient.findAllRoles();
        RoleResponse role = roles.stream().filter(r -> r.name().equals(roleName)).findAny()
                .orElseThrow(() -> new RuntimeException());// TODO: generate specific exception

        List<UserDetailResponse> users = userClient.findOnlineUsersByRoleId(role.id());

        DistributedViolationEntity distributed;
        if (users.isEmpty()) {
            DistributedViolationEntity lastViolation = distributedService.findLastViolation();
            distributed = persistDistributedViolation(violation, lastViolation.getAgentId());
        }
        else {
            Map<UUID, Integer> result = users.stream()
                    .collect(Collectors.toMap(
                            UserDetailResponse::uid,
                            u -> distributedService.findOnProcessViolationByAgentId(u.uid())
                    ));

            int targetLoad = getTargetLoad(result, users);

            UserDetailResponse best = getUserCandidate(targetLoad, users, result);

            // choose the first online user when the best user is null
            if (best == null) {
                best = users.getFirst();
            }

             distributed = persistDistributedViolation(
                    violation,
                    best.uid()
            );
        }

        // TODO: 7.push notification
        System.out.println(distributed);
    }

    private DistributedViolationEntity persistDistributedViolation(
            ViolationEntity violation,
            UUID agentId
    ) {
        DistributedViolationEntity distributed = DistributedViolationEntity.builder()
                .violation(violation)
                .agentId(agentId)
                .priority(violation.getType().getPriority())
                .build();

        return distributedService.insertViolation(distributed);
    }

    private static UserDetailResponse getUserCandidate(
            int targetLoad,
            List<UserDetailResponse> users,
            Map<UUID, Integer> result
    ) {
        return users.stream()
                .min(Comparator.comparingInt(
                        user -> Math.abs(result.getOrDefault(user.uid(), 0) - targetLoad)
                )).orElse(null);
    }

    private static int getTargetLoad(
            Map<UUID, Integer> result,
            List<UserDetailResponse> users
    ) {
        int totalViolation = result.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        int totalUser = users.size();

        // Avoid division by zero
        return totalUser == 0 ? 0 : totalViolation / totalUser;
    }

}
