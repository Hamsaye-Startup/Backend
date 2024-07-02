package org.hamsaye.storages.controllers;


import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageCategoryRequest;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.services.management.StorageCategoryManagement;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/storage/category")
@RequiredArgsConstructor
public class StorageCategoryController {

    private StorageCategoryManagement storageCategoryManagement;

    @Autowired
    public StorageCategoryController(StorageCategoryManagement storageCategoryManagement) {
        this.storageCategoryManagement = storageCategoryManagement;
    }

    @PostMapping
    public ResponseEntity<?> addStorageCategory(@RequestBody StorageCategoryRequest request) {
        Map<String, Functionality> response = storageCategoryManagement.addStorageCategory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateStorageCategory(@RequestBody StorageCategoryRequest request) {
        Map<String, Functionality> response = storageCategoryManagement.updateStorageCategory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteStorageCategory(@PathVariable("code") String code) {
        storageCategoryManagement.deleteStorage(code);

        // TODO generate a format for sending response
        return new ResponseEntity<>("storage's category was deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> findStorageCategoryByCode(@PathVariable("code") String code) {
        StorageCategoryResponse response = storageCategoryManagement.findStorageCategoryByCode(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllStorageCategories() {
        List<StorageCategoryResponse> responses = storageCategoryManagement.findAllStorageCategories();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
