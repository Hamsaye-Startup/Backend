package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.applications.exceptions.IllegalOperationException;
import com.microservices.warehouse.storages.exceptions.EmptyFileUploadedException;
import com.microservices.warehouse.storages.exceptions.FailedUploadFile;
import com.microservices.warehouse.storages.mappers.PolicyMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.PolicyEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyServiceManagement {

    private final PolicyMapper policyMapper;
    private final PolicyJDBCService policyJDBCService;

    private final StorageService storageService;
    private final StorageMapper storageMapper;

    public PolicyDTO insertPolicy(PolicyDTO dto) {

        // convert the dto to entity
        PolicyEntity policy = policyMapper.toPolicy(dto);
        return policyMapper.toDto(policyJDBCService.insertPolicy(policy));
    }

    /*
     * update the policy's code, title and description
     * */
    public PolicyDTO updatePolicy(PolicyDTO dto) {

        // find the policy by code
        PolicyEntity existed = policyJDBCService.findPolicyByCode(dto.code());

        // convert the dto to entity
        PolicyEntity policy = policyMapper.toPolicy(dto);
        return policyMapper.toDto(policyJDBCService.updatePolicy(existed, policy));
    }

    public StorageResponse updateStoragePolicyByCodes(
            Long storageId,
            List<String> policiesCodes,
            UUID userId
    ) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(storageId);

        // check the ownerId by userId
        if (!storage.getOwner().equals(userId)) {
            throw new IllegalOperationException(
                    String.format(
                            "user[%s] try to access secure information",
                            userId
                    )
            );
        }

        policyJDBCService.updateStoragePolicyByCode(policiesCodes, storageId);
        return storageMapper.toResponse(storage);
    }

    public PolicyDTO deletePolicyByCode(String code) {

        // find the policy by code
        PolicyEntity policy = policyJDBCService.findPolicyByCode(code);

        // delete the policy by code
        policyJDBCService.deletePolicyByCode(code);
        return policyMapper.toDto(policy);
    }

    public PolicyDTO findPolicyByCode(String code) {
        return policyMapper.toDto(policyJDBCService.findPolicyByCode(code));
    }

    public Page<PolicyDTO> findAllPolicies(Pageable pageable) {
        return policyJDBCService.findAllPolicies(pageable)
                .map(policyMapper::toDto);
    }

    public List<PolicyDTO> findAllPoliciesByStorageId(Long storageId) {
        return policyJDBCService.findPoliciesByStorageId(storageId)
                .stream().map(policyMapper::toDto).toList();
    }

    public void uploadPolicyDocumentByCode(String code, MultipartFile multipartFile) {

        // check the file is not empty
        if (multipartFile.isEmpty()) {
            throw new EmptyFileUploadedException(code);
        }

        try {
            // TODO: persist object in bucket
            byte[] bytes = multipartFile.getBytes();


        } catch (IOException e) {
            throw new FailedUploadFile(e.getCause(), code);
        }

        // persist the key in db
    }

    public byte[] downloadPolicyDocumentByCode(String code) {

        // find the policy by code
        PolicyEntity policy = policyJDBCService.findPolicyByCode(code);

        // TODO: find the object by key

        return null;
    }
}
