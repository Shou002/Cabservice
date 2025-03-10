package com.projectSer.projectServices.repositories;

import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByStatus(VehicleStatus status);
    List<Vehicle> findByType(vehicleType type);
    List<Vehicle> findByDriver_DriverId(int driverId);
    default List<Vehicle> findAvailableVehicles() {
        return findByStatus(VehicleStatus.AVAILABLE);
    }
}
