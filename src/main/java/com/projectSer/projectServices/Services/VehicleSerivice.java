package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.vehicle;
import com.projectSer.projectServices.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class VehicleSerivice {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<vehicle> getAvailbleVehicles() {
        return vehicleRepository.findAvailableVehicles();
    }

    public List<vehicle> getVehiclesByType(vehicleType type) {
        return vehicleRepository.findByType(type);
    }

    public List<vehicle> getVehiclesByDriverId(int driverId) {
        return vehicleRepository.findByDriver_DriverId(driverId);
    }

    public List<vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
