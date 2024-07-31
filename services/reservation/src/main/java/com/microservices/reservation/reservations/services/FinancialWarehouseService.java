package com.microservices.reservation.reservations.services;

import com.microservices.reservation.reservations.exceptions.ImpossibleTotalInstallmentsException;
import com.microservices.reservation.reservations.responses.OrderWarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialWarehouseService {

    private final TimePartitionService partitionService;

    public void calculateTotalFees(OrderWarehouseResponse order) {

        // from date -> to date :: rentPer -> day :: total installments
        long days = partitionService.differenceInDay(order.getToDate().getTime() - order.getFromDate().getTime());
        Double amountPerDay = order.getRentPer();

        // check the total installments
        if (order.getTotalInstallmentsNumber() <= days) {

            // calculate the total & section & rent per section
            Double total = calculateTotalFees(days, amountPerDay);
            long section = partitionService.calculatePartitioning(days, order.getTotalInstallmentsNumber());
            Double rentPer = amountPerDay * section;

            // update the response
            order.setTotalFees(total);
            order.setRentPer(rentPer);
            order.setPerDate(partitionService.calculateDateEnum(section));

        } else {
            throw new ImpossibleTotalInstallmentsException(order.getTotalInstallmentsNumber() + ">" + days);
        }
    }

    private Double calculateTotalFees(long days, Double amountPerDay) {
        return amountPerDay * days;
    }
}
