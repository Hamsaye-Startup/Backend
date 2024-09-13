package com.hamsaye.report.violations.mappers;

import com.hamsaye.report.violations.models.ViolationResultStatus;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

@Service
public class ViolationResultStatusMapper {

    public ViolationResultStatus convertViolationResultStatus(String status) {
        try {
            return ViolationResultStatus.valueOf(status);
        } catch (IllegalArgumentException ex) {
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(ViolationResultStatus.class),
                    status,
                    ex.getCause()
            );
        }
    }
}
