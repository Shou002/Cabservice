package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.models.Vehicle;
import com.projectSer.projectServices.repositories.DriverRepository;
import com.projectSer.projectServices.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findAvailableVehicles();
    }

    public List<Vehicle> getVehiclesByType(vehicleType type) {
        return vehicleRepository.findByType(type);
    }

    public List<Vehicle> getVehiclesByDriverId(int driverId) {
        return vehicleRepository.findByDriver_DriverId(driverId);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle addVehicle(String veh_name, String type, double price, int driverId, int capacity) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setVeh_name(veh_name);

        try {
            vehicle.setType(vehicleType.valueOf(type.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid vehicle type: " + type);
        }

        vehicle.setPrice(price);
        vehicle.setDriver(driver);
        vehicle.setCapacity(capacity);
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        return vehicleRepository.save(vehicle);
    }

}
