package com.microservices.warehouse.warehouses.mappers;

import com.microservices.warehouse.applications.messages.ResponseMessage;
import com.microservices.warehouse.customer.CustomerClient;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.requests.NewWarehouseRequest;
import com.microservices.warehouse.warehouses.requests.WarehouseRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import com.microservices.warehouse.warehouses.responses.LimitedWarehouseResponse;
import com.microservices.warehouse.warehouses.responses.PolicyResponse;
import com.microservices.warehouse.warehouses.responses.WarehouseResponse;
import com.microservices.warehouse.warehouses.services.FeatureService;
import com.microservices.warehouse.warehouses.services.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseMapper {

    private final FeatureMapper featureMapper;
    private final PolicyMapper policyMapper;

    private final FeatureService featureService;
    private final PolicyService policyService;

    // map all the policies
    private Set<PolicyResponse> mapPoliciesToResponse(Set<PolicyEntity> entities) {
        return entities.stream()
                .map(policyMapper::toResponse)
                .collect(Collectors.toSet());
    }

    // map all the features
    private Set<FeatureResponse> mapFeaturesToResponse(Set<FeatureEntity> entities) {
        return entities.stream()
                .map(featureMapper::toResponse)
                .collect(Collectors.toSet());
    }

    // find all the policies
    private Set<PolicyEntity> fetchPolicies(Set<String> policies) {
        return policies.stream()
                .map(policyService::findByCode)
                .collect(Collectors.toSet());
    }

    // find all the features
    private Set<FeatureEntity> fetchFeatures(Set<String> features) {
        return features.stream()
                .map(featureService::findByCode)
                .collect(Collectors.toSet());
    }

    public WarehouseEntity toWarehouseEntity(NewWarehouseRequest request, String token) {

        CustomerClient client = new CustomerClient();
        ResponseMessage message = client.findCustomerById(request.owner(), token);

        System.out.println("message: " + message);
        System.out.println("result: " + message.result());

        return WarehouseEntity.builder()
                .owner(request.owner())
                .category(request.category())
                .features(fetchFeatures(request.features()))
                .policies(fetchPolicies(request.policies()))
                .width(request.width())
                .height(request.height())
                .amount(request.amount())
                .discountAmount(request.discountAmount())
                .desc(request.desc())
                .build();
    }

    public WarehouseEntity toWarehouseEntity(WarehouseRequest request, WarehouseEntity warehouse) {
        return WarehouseEntity.builder()
                .id(request.id())
                .owner(request.owner())
                .category(request.category() == null ? warehouse.getCategory() : request.category())
                .features(fetchFeatures(request.features()))
                .policies(fetchPolicies(request.policies()))
                .width(request.width() == null ? warehouse.getWidth() : request.width())
                .height(request.height() == null ? warehouse.getHeight() : request.height())
                .amount(request.amount() == null ? warehouse.getAmount() : request.amount())
                .discountAmount(request.discountAmount() == null ? warehouse.getDiscountAmount() : request.discountAmount())
                .desc(request.desc() == null ? warehouse.getDesc() : request.desc())
                .build();
    }

    public WarehouseResponse toResponse(WarehouseEntity warehouse) {
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .owner(warehouse.getOwner())
                .category(warehouse.getCategory())
                .features(mapFeaturesToResponse(warehouse.getFeatures()))
                .policies(mapPoliciesToResponse(warehouse.getPolicies()))
                .width(warehouse.getWidth())
                .height(warehouse.getHeight())
                .amount(warehouse.getAmount())
                .discountAmount(warehouse.getDiscountAmount())
                .desc(warehouse.getDesc())
                .marked(warehouse.isMarked())
                .liked(warehouse.isLiked())
                .build();
    }

    public LimitedWarehouseResponse toLimitResponse(WarehouseEntity warehouse) {
        return LimitedWarehouseResponse.builder()
                .id(warehouse.getId())
                .owner(warehouse.getOwner())
                .category(warehouse.getCategory())
                .createAt(warehouse.getCreatedAt())
                .width(warehouse.getWidth())
                .height(warehouse.getHeight())
                .amount(warehouse.getAmount())
                .discountAmount(warehouse.getDiscountAmount())
                .marked(warehouse.isMarked())
                .liked(warehouse.isLiked())
                .build();
    }
}
