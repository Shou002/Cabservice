package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.Vehicle;
import com.projectSer.projectServices.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAvailbleVehicles() {
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
}
