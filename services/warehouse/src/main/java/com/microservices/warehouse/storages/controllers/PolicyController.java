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

/**
 * This controller handles HTTP requests related to policies for storage items.
 * It interacts with the {@link PolicyServiceManagement} to manage policies and their associated documents.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage/policy")
@RequiredArgsConstructor
public class PolicyController {

    /**
     * Mapper for transforming between different representations of policies.
     * See {@link com.microservices.warehouse.applications.mapper.MessageMapper} for more details.
     */
    private final MessageMapper mapper;

    /**
     * Service management for handling policy-related operations.
     * See {@link com.microservices.warehouse.storages.services.PolicyServiceManagement} for more details.
     */
    private final PolicyServiceManagement policyServiceManagement;

    /**
     * Extracts the user ID from the HTTP request header.
     * @param request the HTTP request containing the user ID header
     * @return the extracted user ID as a {@link UUID}
     * @throws AuthenticationCredentialNotFoundException if the user ID header is missing
     * @since 1.0
     */
    private UUID findUserByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    /**
     * Adds a new policy to the system.
     * @param policy the policy to be added
     * @return ResponseEntity containing the added policy information
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> addPolicy(
            @RequestBody @Valid PolicyDTO policy
    ) {
        PolicyDTO response = policyServiceManagement.insertPolicy(policy);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates an existing policy in the system.
     * @param policy the policy with updated information
     * @return ResponseEntity containing the updated policy information
     * @since 1.0
     */
    @PutMapping
    public ResponseEntity<?> updatePolicy(
            @RequestBody @Valid PolicyDTO policy
    ) {
        PolicyDTO response = policyServiceManagement.updatePolicy(policy);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates policies for a specific storage item based on policy codes.
     * @param storageId the ID of the storage item
     * @param policyCodes the list of policy codes to be updated
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the updated storage information
     * @since 1.0
     */
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

    /**
     * Removes a policy by its code.
     * @param code the code of the policy to be removed
     * @return ResponseEntity containing the removed policy information
     * @since 1.0
     */
    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> removePolicy(
            @PathVariable("code") String code
    ) {
        PolicyDTO response = policyServiceManagement.deletePolicyByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds a policy by its code.
     * @param code the code of the policy to be found
     * @return ResponseEntity containing the found policy information
     * @since 1.0
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<?> findPolicyByCode(
            @PathVariable("code") String code
    ) {
        PolicyDTO response = policyServiceManagement.findPolicyByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds all policies with pagination support.
     * @param pageable pagination information
     * @return ResponseEntity containing a page of policies
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> findAllPolicies(
            Pageable pageable
    ) {
        Page<PolicyDTO> responses = policyServiceManagement.findAllPolicies(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Finds all policies associated with a specific storage item.
     * @param storageId the ID of the storage item
     * @return ResponseEntity containing a list of policies associated with the storage item
     * @since 1.0
     */
    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllPoliciesByStorageId(
            @PathVariable("storageId") Long storageId
    ) {
        List<PolicyDTO> responses = policyServiceManagement.findAllPoliciesByStorageId(
                storageId
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Uploads a policy document associated with a specific policy code.
     * @param code the code of the policy for which the document is being uploaded
     * @param multipartFile the policy document to be uploaded
     * @return ResponseEntity indicating the success of the upload
     * @since 1.0
     */
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

    /**
     * Downloads a policy document associated with a specific policy code.
     * @param code the code of the policy for which the document is being downloaded
     * @return byte array containing the policy document
     * @since 1.0
     */
    @GetMapping("/code/{code}/document")
    public byte[] downloadPolicyDocument(
            @PathVariable("code") String code
    ) {
        return policyServiceManagement.downloadPolicyDocumentByCode(code);
    }
}
