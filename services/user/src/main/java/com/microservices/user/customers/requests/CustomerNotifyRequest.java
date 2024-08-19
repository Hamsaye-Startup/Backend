package com.microservices.user.customers.requests;

import com.microservices.user.customers.models.CustomerDTO;
import com.microservices.user.users.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerNotifyRequest(

        CustomerDTO customerInfo,
        String message,
        CustomerNotifyType type

) implements Serializable {
}
