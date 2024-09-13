package com.hamsaye.report.kafka.requests;

import lombok.Builder;

@Builder
public record WarehouseViolationNotifyRequest(
        Long warehouseId,
        String message,
        WarehouseViolationNotifyType type
) {
}
