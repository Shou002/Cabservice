package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.Services.VehicleService;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class vehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles () {
        List<Vehicle> vehicles = vehicleService.getAvailbleVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable vehicleType type) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByType(type);
        return  ResponseEntity.ok(vehicles);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByDriverId(@PathVariable int driverId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByDriverId(driverId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
