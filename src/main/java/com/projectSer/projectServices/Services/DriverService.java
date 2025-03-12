package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.DriverStatus;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public Driver addDriver(String name, int age, String pn, String address) {
        Driver driver = new Driver();
        driver.setName(name);
        driver.setAge(age);
        driver.setPn(pn);
        driver.setAddress(address);
        driver.setStatus(DriverStatus.AVAILABLE);

        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}

