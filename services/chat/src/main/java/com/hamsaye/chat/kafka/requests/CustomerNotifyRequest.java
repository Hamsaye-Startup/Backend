package com.hamsaye.chat.kafka.requests;

import com.hamsaye.chat.kafka.models.CustomerDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerNotifyRequest(

        CustomerDTO customerInfo,
        String message,
        CustomerNotifyType type

) implements Serializable {
}
