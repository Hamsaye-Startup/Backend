package org.hamsaye.storages.controllers;


import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.services.management.StorageCategoryManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/storage/category")
@RequiredArgsConstructor
public class StorageCategoryController {

    private StorageCategoryManagement storageCategoryManagement;

    @Autowired
    public StorageCategoryController(StorageCategoryManagement storageCategoryManagement) {
        this.storageCategoryManagement = storageCategoryManagement;
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> findStorageCategoryByCode(@PathVariable("code") String code) {
        StorageCategoryResponse response = storageCategoryManagement.findCategoryByCode(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
