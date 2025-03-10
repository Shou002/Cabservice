package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.Services.VehicleSerivice;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class vehicleController {
    @Autowired
    private VehicleSerivice vehicleService;

    @GetMapping("/available")
    public ResponseEntity<List<vehicle>> getAvailableVehicles () {
        List<vehicle> vehicles = vehicleService.getAvailbleVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<vehicle>> getVehiclesByType(@PathVariable vehicleType type) {
        List<vehicle> vehicles = vehicleService.getVehiclesByType(type);
        return  ResponseEntity.ok(vehicles);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<vehicle>> getVehiclesByDriverId(@PathVariable int driverId) {
        List<vehicle> vehicles = vehicleService.getVehiclesByDriverId(driverId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping
    public ResponseEntity<List<vehicle>> getAllVehicles() {
        List<vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
