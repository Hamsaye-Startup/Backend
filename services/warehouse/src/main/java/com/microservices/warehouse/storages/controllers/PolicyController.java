package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.storages.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.responses.StorageResponse;
import com.microservices.warehouse.storages.services.PolicyServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final MessageMapper mapper;
    private final PolicyServiceManagement policyServiceManagement;

    private UUID findUserByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    @PostMapping
    public ResponseEntity<?> addPolicy(
            @RequestBody @Valid PolicyDTO policy
    ) {
        PolicyDTO response = policyServiceManagement.insertPolicy(policy);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping
    public ResponseEntity<?> updatePolicy(
            @RequestBody @Valid PolicyDTO policy
    ) {
        PolicyDTO response = policyServiceManagement.updatePolicy(policy);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping("/storage/id/{storageId}")
    public ResponseEntity<?> updateStoragePolicies(
            @PathVariable("storageId") Long storageId,
            @RequestBody List<String> policyCodes,
            HttpServletRequest request
    ) {
        StorageResponse response = policyServiceManagement.updateStoragePolicyByCodes(
                storageId,
                policyCodes,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> removePolicy(
            @PathVariable("code") String code
    ) {
        PolicyDTO response = policyServiceManagement.deletePolicyByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findPolicyByCode(
            @PathVariable("code") String code
    ) {
        PolicyDTO response = policyServiceManagement.findPolicyByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping
    public ResponseEntity<?> findAllPolicies(
            Pageable pageable
    ) {
        Page<PolicyDTO> responses = policyServiceManagement.findAllPolicies(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllPoliciesByStorageId(
            @PathVariable("storageId") Long storageId
    ) {
        List<PolicyDTO> responses = policyServiceManagement.findAllPoliciesByStorageId(
                storageId
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @PostMapping(
            value = "/code/{code}/document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uploadPolicyDocument(
            @PathVariable("code") String code,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        policyServiceManagement.uploadPolicyDocumentByCode(code, multipartFile);
        return ResponseEntity.ok(mapper.toResponse());
    }

    @GetMapping("/code/{code}/document")
    public byte[] downloadPolicyDocument(
            @PathVariable("code") String code
    ) {
        return policyServiceManagement.downloadPolicyDocumentByCode(code);
    }
}
