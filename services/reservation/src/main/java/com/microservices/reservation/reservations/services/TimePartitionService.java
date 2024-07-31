package com.microservices.reservation.reservations.services;

import com.microservices.reservation.reservations.models.ForEachDateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TimePartitionService {

    public long differenceInDay(long time) {
        return TimeUnit.MILLISECONDS.toDays(time) % 365;
    }

    public long calculatePartitioning(long days, long installments) {
        return days / installments;
    }

    public ForEachDateEnum calculateDateEnum(long section) {

        if (section <= 29) {
            return ForEachDateEnum.DAY;
        } else if (section <= 365) {
            return ForEachDateEnum.MONTH;
        } else {
            return ForEachDateEnum.YEAR;
        }
    }

    public List<Date> calculateListOfInstallmentDates(long days, long installment, Date from) {
        long section = calculatePartitioning(days, installment);
        List<Date> installments = new ArrayList<>();

        // Convert Date to LocalDate directly
        LocalDate currDate = from.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        for (int counter = 1; counter <= installment; counter++) {
            // Add sections to the current date
            LocalDate installmentDate = currDate.plusDays(section * counter);
            installments.add(Date.from(installmentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return installments;
    }
}
