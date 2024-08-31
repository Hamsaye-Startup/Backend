package com.microservices.user.customers.requests;

import com.microservices.user.customers.dto.CustomerDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerNotifyRequest(

        CustomerDTO customerInfo,
        String message,
        CustomerNotifyType type

) implements Serializable {
}
