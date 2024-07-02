package org.hamsaye.geo.controllers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.dtos.requests.CityRequest;
import org.hamsaye.geo.dtos.responses.CityResponse;
import org.hamsaye.geo.services.management.CityServiceManagement;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/city")
@RequiredArgsConstructor
public class CityController {
    private CityServiceManagement cityServiceManagement;

    @Autowired
    public CityController(CityServiceManagement cityServiceManagement) {
        this.cityServiceManagement = cityServiceManagement;
    }

    @PostMapping
    public ResponseEntity<?> addStorage(@RequestBody CityRequest cityRequest) {
        Map<String, Functionality> response = cityServiceManagement.addCity(cityRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStorage(@RequestBody CityRequest cityRequest) {
        Map<String, Functionality> response = cityServiceManagement.updateCity(cityRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteStorage(@PathVariable("uid") UUID uid) {
        cityServiceManagement.deleteCity(uid);

        // TODO generate a format for sending response
        return new ResponseEntity<>("city was deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showAllStorages() {
        List<CityResponse> responses = cityServiceManagement.findAllCities();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
