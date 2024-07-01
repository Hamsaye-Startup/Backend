package org.hamsaye.storages.controllers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageRequest;
import org.hamsaye.storages.dtos.responses.StorageResponse;
import org.hamsaye.storages.services.management.StorageServiceManagement;
import org.hamsaye.utils.log.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private StorageServiceManagement storageServiceManagement;

    @Autowired
    public StorageController(StorageServiceManagement storageServiceManagement) {
        this.storageServiceManagement = storageServiceManagement;
    }

    @PostMapping
    public ResponseEntity<?> addStorage(@RequestBody StorageRequest storageRequest) {
        Map<String, Functionality> response = storageServiceManagement.addStorage(storageRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStorage(@RequestBody StorageRequest storageRequest) {
        Map<String, Functionality> response = storageServiceManagement.updateStorage(storageRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteStorage(@PathVariable("uid") UUID uid) {
        storageServiceManagement.deleteStorage(uid);

        // TODO generate a format for sending response
        return new ResponseEntity<>("storage was deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showAllStorages() {
        List<StorageResponse> responses = storageServiceManagement.findAllStorages();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
