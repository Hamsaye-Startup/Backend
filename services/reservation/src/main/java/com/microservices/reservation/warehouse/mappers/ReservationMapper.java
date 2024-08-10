package com.microservices.reservation.warehouse.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    private final ObjectMapper mapper;

    public String toString(ReservationResponse response) throws JsonProcessingException {
        return mapper.writeValueAsString(response);
    }
}
