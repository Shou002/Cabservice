package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.Services.DriverService;
import com.projectSer.projectServices.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping("/add")
    public ResponseEntity<?> addDriver(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String pn,
            @RequestParam String address) {
        try {
            Driver driver = driverService.addDriver(name, age, pn, address);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

