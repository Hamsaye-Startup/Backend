package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.application.exceptions.IllegalOperationException;
import com.microservices.warehouse.application.exceptions.EmptyFileUploadedException;
import com.microservices.warehouse.application.exceptions.FailedUploadFileException;
import com.microservices.warehouse.storages.mappers.PolicyMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.PolicyEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service class that provides operations related to policies and interacts with the Policy Controller.
 * It handles CRUD operations, policy document uploads, and storage-specific policy updates.
 *
 * <p>This service uses {@link PolicyJDBCService} for database operations and {@link PolicyMapper} for
 * converting between entities and DTOs.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PolicyServiceManagement {

    /**
     * Mapper for converting between {@link PolicyDTO} and {@link PolicyEntity}.
     */
    private final PolicyMapper policyMapper;

    /**
     * Service for handling database operations related to policies.
     */
    private final PolicyJDBCService policyJDBCService;

    /**
     * Service for handling operations related to storage entities.
     */
    private final StorageService storageService;

    /**
     * Mapper for converting between {@link StorageEntity} and {@link StorageResponse}.
     */
    private final StorageMapper storageMapper;

    /**
     * Adds a new policy.
     *
     * @param dto The {@link PolicyDTO} containing policy information provided by the client.
     * @return The created {@link PolicyDTO}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public PolicyDTO insertPolicy(PolicyDTO dto) {

        // Convert DTO to entity
        PolicyEntity policy = policyMapper.toPolicy(dto);
        return policyMapper.toDto(policyJDBCService.insertPolicy(policy));
    }

    /**
     * Updates an existing policy.
     *
     * @param dto The {@link PolicyDTO} containing policy information provided by the client.
     * @return The updated {@link PolicyDTO}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public PolicyDTO updatePolicy(PolicyDTO dto) {

        // Find the existing policy by code
        PolicyEntity existed = policyJDBCService.findPolicyByCode(dto.code());

        // Convert DTO to entity
        PolicyEntity policy = policyMapper.toPolicy(dto);
        return policyMapper.toDto(policyJDBCService.updatePolicy(existed, policy));
    }

    /**
     * Updates the policies associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @param policiesCodes The list of policy codes to be associated with the storage.
     * @param userId The ID of the currently authenticated user.
     * @return The updated {@link StorageResponse}.
     * @throws IllegalOperationException If the user does not have permission to access the storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse updateStoragePolicyByCodes(
            Long storageId,
            List<String> policiesCodes,
            UUID userId
    ) {

        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        // Check if the user is the owner of the storage
        if (!storage.getOwner().equals(userId)) {
            throw new IllegalOperationException(
                    String.format("User[%s] attempted to access secure information", userId)
            );
        }

        policyJDBCService.updateStoragePolicyByCode(policiesCodes, storageId);
        return storageMapper.toResponse(storage);
    }

    /**
     * Deletes a policy by its code.
     *
     * @param code The code of the policy to be deleted.
     * @return The deleted {@link PolicyDTO}.
     * @since 1.0
     */
    public PolicyDTO deletePolicyByCode(String code) {

        // Find the policy by code
        PolicyEntity policy = policyJDBCService.findPolicyByCode(code);

        // Delete the policy by code
        policyJDBCService.deletePolicyByCode(code);
        return policyMapper.toDto(policy);
    }

    /**
     * Finds a policy by its code.
     *
     * @param code The code of the policy to be found.
     * @return The {@link PolicyDTO} corresponding to the policy code.
     * @since 1.0
     */
    public PolicyDTO findPolicyByCode(String code) {
        return policyMapper.toDto(policyJDBCService.findPolicyByCode(code));
    }

    /**
     * Finds all policies with pagination support.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link PolicyDTO}.
     * @since 1.0
     */
    public Page<PolicyDTO> findAllPolicies(Pageable pageable) {
        return policyJDBCService.findAllPolicies(pageable)
                .map(policyMapper::toDto);
    }

    /**
     * Finds all policies associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @return A {@link List} of {@link PolicyDTO} associated with the specified storage.
     * @since 1.0
     */
    public List<PolicyDTO> findAllPoliciesByStorageId(Long storageId) {
        return policyJDBCService.findPoliciesByStorageId(storageId)
                .stream().map(policyMapper::toDto).toList();
    }

    /**
     * Uploads a policy document.
     *
     * <p>Note: Documents are stored in cloud storage.</p>
     *
     * @param code The code of the policy for which the document is being uploaded.
     * @param multipartFile The file to be uploaded.
     * @throws EmptyFileUploadedException If the uploaded file is empty.
     * @throws FailedUploadFileException If an error occurs during the file upload.
     * @since 1.0
     */
    public void uploadPolicyDocumentByCode(String code, MultipartFile multipartFile) {

        // Check if the file is not empty
        if (multipartFile.isEmpty()) {
            throw new EmptyFileUploadedException(code);
        }

        try {
            // TODO: Persist object in bucket
            byte[] bytes = multipartFile.getBytes();

        } catch (IOException e) {
            throw new FailedUploadFileException(e.getMessage());
        }

        // TODO: Persist the key in the database
    }

    /**
     * Downloads a policy document.
     *
     * <p>Note: Documents are stored in cloud storage.</p>
     *
     * @param code The code of the policy for which the document is being downloaded.
     * @return The document as a byte array.
     * @since 1.0
     */
    public byte[] downloadPolicyDocumentByCode(String code) {

        // Find the policy by code
        PolicyEntity policy = policyJDBCService.findPolicyByCode(code);

        // TODO: Find the object by key

        return null;
    }
}
