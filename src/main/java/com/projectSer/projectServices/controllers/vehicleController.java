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
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
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

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(
            @RequestParam String veh_name,
            @RequestParam String type,
            @RequestParam double price,
            @RequestParam int driverId,
            @RequestParam int capacity
    ) {
        try {
            Vehicle vehicle = vehicleService.addVehicle(veh_name, type, price, driverId, capacity);
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
