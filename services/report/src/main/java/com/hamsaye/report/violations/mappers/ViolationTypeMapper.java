package com.hamsaye.report.violations.mappers;

import com.hamsaye.report.violations.models.ViolationTypeCategory;
import com.hamsaye.report.violations.models.ViolationTypeEntity;
import com.hamsaye.report.violations.responses.ViolationTypeResponse;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

@Service
public class ViolationTypeMapper {

    public ViolationTypeResponse toResponse(ViolationTypeEntity type) {
        return ViolationTypeResponse.builder()
                .code(type.getCode())
                .title(type.getTitle())
                .category(type.getGroup())
                .priority(type.getPriority())
                .desc(type.getDesc())
                .build();
    }

    public ViolationTypeCategory convertViolationTypeCategory(String category) {
        try {
            return ViolationTypeCategory.valueOf(category);
        } catch (IllegalArgumentException ex) {
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(ViolationTypeCategory.class),
                    category,
                    ex.getCause()
            );
        }
    }
}
