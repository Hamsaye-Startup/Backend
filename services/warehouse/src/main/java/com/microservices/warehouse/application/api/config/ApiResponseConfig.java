package com.microservices.warehouse.application.api.config;

import com.microservices.warehouse.application.api.response.ResponseFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api")
public class ApiResponseConfig {

    private Map<String, ResponseFormat> responses;

    public ResponseFormat getResponseByKey(String key) {
        return responses.get(key);
    }
}
