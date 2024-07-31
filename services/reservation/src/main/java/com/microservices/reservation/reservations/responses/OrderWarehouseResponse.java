package com.microservices.reservation.reservations.responses;

import com.microservices.reservation.reservations.models.ForEachDateEnum;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderWarehouseResponse {

    private Long warehouse;

    private Date fromDate;

    private Date toDate;

    private Double totalFees;

    private Double rentPer;

    private ForEachDateEnum perDate;

    private Integer totalInstallmentsNumber;
}
