package org.hamsaye.geo.controllers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.dtos.requests.StorageLocationRequest;
import org.hamsaye.geo.dtos.responses.StorageLocationResponse;
import org.hamsaye.geo.services.management.StorageLocationManagement;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/location/storage")
@RequiredArgsConstructor
public class StorageLocationController {
    private StorageLocationManagement storageLocationManagement;

    @Autowired
    public StorageLocationController(StorageLocationManagement storageLocationManagement) {
        this.storageLocationManagement = storageLocationManagement;
    }

    @PostMapping
    public ResponseEntity<?> addStorage(@RequestBody StorageLocationRequest locationRequest) {
        Map<String, Functionality> response = storageLocationManagement.addStorageLocation(locationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStorage(@RequestBody StorageLocationRequest locationRequest) {
        Map<String, Functionality> response = storageLocationManagement.updateStorageLocation(locationRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStorage(@PathVariable("id") Long id) {
        storageLocationManagement.deleteStorageLocation(id);

        // TODO generate a format for sending response
        return new ResponseEntity<>("storage location was deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showAllStorages() {
        List<StorageLocationResponse> responses = storageLocationManagement.findAllStorageLocations();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
