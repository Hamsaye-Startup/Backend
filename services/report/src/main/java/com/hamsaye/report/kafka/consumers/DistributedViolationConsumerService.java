package com.hamsaye.report.kafka.consumers;

import com.hamsaye.report.kafka.requests.DistributedViolationNotifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedViolationConsumerService {

    @KafkaListener(
            id = "violation-agent-changed-listener-id",
            topics = "topic-violation-agent-changed",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void changeViolationAgentListener(
            DistributedViolationNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        // TODO: 1.persist a log of changes

        // TODO: 2.push notification
    }
}
