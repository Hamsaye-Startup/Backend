package org.hamsaye.storages.controllers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageFeatureRequest;
import org.hamsaye.storages.dtos.responses.StorageFeatureResponse;
import org.hamsaye.storages.services.management.StorageFeatureManagement;
import org.hamsaye.utils.log.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage/feature")
@RequiredArgsConstructor
public class StorageFeatureController {

    private StorageFeatureManagement storageFeatureManagement;

    @Autowired
    public StorageFeatureController(StorageFeatureManagement storageFeatureManagement) {
        this.storageFeatureManagement = storageFeatureManagement;
    }

    @PostMapping
    public ResponseEntity<?> addStorageCategory(@RequestBody StorageFeatureRequest request) {
        Map<String, Functionality> response = storageFeatureManagement.addStorageFeature(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateStorageCategory(@RequestBody StorageFeatureRequest request) {
        Map<String, Functionality> response = storageFeatureManagement.updateStorageFeature(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteStorageCategory(@PathVariable("uid") UUID uid) {
        storageFeatureManagement.deleteStorage(uid);

        // TODO generate a format for sending response
        return new ResponseEntity<>("storage's category was deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<?> findStorageCategoryByCode(@PathVariable("uid") UUID uid) {
        StorageFeatureResponse response = storageFeatureManagement.findStorageFeatureById(uid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllStorageCategories() {
        List<StorageFeatureResponse> responses = storageFeatureManagement.findAllStorageFeatures();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
