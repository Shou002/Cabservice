package com.projectSer.projectServices.repositories;

import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<vehicle, Integer> {
    List<vehicle> findByStatus(VehicleStatus status);
    List<vehicle> findByType(vehicleType type);
    List<vehicle> findByDriver_DriverId(int driverId);
    default List<vehicle> findAvailableVehicles() {
        return findByStatus(VehicleStatus.available);
    }
}
