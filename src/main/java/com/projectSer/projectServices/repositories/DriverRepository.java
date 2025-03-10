package com.projectSer.projectServices.repositories;

import com.projectSer.projectServices.enums.DriverStatus;
import com.projectSer.projectServices.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    List<Driver> findByStatus(DriverStatus status);
}
