package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.vehicleType;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.models.Vehicle;
import com.projectSer.projectServices.repositories.DriverRepository;
import com.projectSer.projectServices.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Driver testDriver;
    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test driver
        testDriver = new Driver();
        testDriver.setDriverId(1);
        testDriver.setName("Test Driver");

        // Setup test vehicle
        testVehicle = new Vehicle();
        testVehicle.setVeh_name("Test Vehicle");
        testVehicle.setType(vehicleType.SEDAN);
        testVehicle.setPrice(100.0);
        testVehicle.setDriver(testDriver);
        testVehicle.setCapacity(4);
        testVehicle.setStatus(VehicleStatus.AVAILABLE);
    }

    @Test
    void getAvailableVehicles_shouldReturnListOfAvailableVehicles() {
        // Arrange
        List<Vehicle> availableVehicles = Arrays.asList(
                createVehicle("Vehicle 1", vehicleType.SEDAN, 100.0, testDriver, 4),
                createVehicle("Vehicle 2", vehicleType.SUV, 150.0, testDriver, 6)
        );

        when(vehicleRepository.findAvailableVehicles()).thenReturn(availableVehicles);

        // Act
        List<Vehicle> result = vehicleService.getAvailableVehicles();

        // Assert
        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAvailableVehicles();
    }

    @Test
    void getVehiclesByType_shouldReturnListOfVehiclesByType() {
        // Arrange
        List<Vehicle> sedanVehicles = Arrays.asList(
                createVehicle("Sedan 1", vehicleType.SEDAN, 100.0, testDriver, 4),
                createVehicle("Sedan 2", vehicleType.SEDAN, 110.0, testDriver, 5)
        );

        when(vehicleRepository.findByType(vehicleType.SEDAN)).thenReturn(sedanVehicles);

        // Act
        List<Vehicle> result = vehicleService.getVehiclesByType(vehicleType.SEDAN);

        // Assert
        assertEquals(2, result.size());
        assertEquals(vehicleType.SEDAN, result.get(0).getType());
        assertEquals(vehicleType.SEDAN, result.get(1).getType());
        verify(vehicleRepository, times(1)).findByType(vehicleType.SEDAN);
    }

    @Test
    void getVehiclesByDriverId_shouldReturnListOfVehiclesByDriverId() {
        // Arrange
        List<Vehicle> driverVehicles = Arrays.asList(
                createVehicle("Driver Vehicle 1", vehicleType.SEDAN, 100.0, testDriver, 4),
                createVehicle("Driver Vehicle 2", vehicleType.SUV, 150.0, testDriver, 6)
        );

        when(vehicleRepository.findByDriver_DriverId(1)).thenReturn(driverVehicles);

        // Act
        List<Vehicle> result = vehicleService.getVehiclesByDriverId(1);

        // Assert
        assertEquals(2, result.size());
        assertEquals(testDriver, result.get(0).getDriver());
        assertEquals(testDriver, result.get(1).getDriver());
        verify(vehicleRepository, times(1)).findByDriver_DriverId(1);
    }

    @Test
    void getAllVehicles_shouldReturnListOfAllVehicles() {
        // Arrange
        List<Vehicle> allVehicles = Arrays.asList(
                createVehicle("Vehicle 1", vehicleType.SEDAN, 100.0, testDriver, 4),
                createVehicle("Vehicle 2", vehicleType.SUV, 150.0, testDriver, 6),
                createVehicle("Vehicle 3", vehicleType.VAN, 200.0, testDriver, 8)
        );

        when(vehicleRepository.findAll()).thenReturn(allVehicles);

        // Act
        List<Vehicle> result = vehicleService.getAllVehicles();

        // Assert
        assertEquals(3, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void addVehicle_withValidData_shouldSaveAndReturnVehicle() {
        // Arrange
        when(driverRepository.findById(1)).thenReturn(Optional.of(testDriver));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(testVehicle);

        // Act
        Vehicle result = vehicleService.addVehicle("Test Vehicle", "SEDAN", 100.0, 1, 4);

        // Assert
        assertNotNull(result);
        assertEquals("Test Vehicle", result.getVeh_name());
        assertEquals(vehicleType.SEDAN, result.getType());
        assertEquals(100.0, result.getPrice());
        assertEquals(testDriver, result.getDriver());
        assertEquals(4, result.getCapacity());
        assertEquals(VehicleStatus.AVAILABLE, result.getStatus());

        verify(driverRepository, times(1)).findById(1);
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void addVehicle_withNonExistentDriver_shouldThrowRuntimeException() {
        // Arrange
        when(driverRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.addVehicle("Test Vehicle", "SEDAN", 100.0, 999, 4);
        });

        assertEquals("Driver not found", exception.getMessage());
        verify(driverRepository, times(1)).findById(999);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void addVehicle_withInvalidVehicleType_shouldThrowRuntimeException() {
        // Arrange
        when(driverRepository.findById(1)).thenReturn(Optional.of(testDriver));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.addVehicle("Test Vehicle", "INVALID_TYPE", 100.0, 1, 4);
        });

        assertTrue(exception.getMessage().contains("Invalid vehicle type"));
        verify(driverRepository, times(1)).findById(1);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    // Helper method to create Vehicle objects
    private Vehicle createVehicle(String name, vehicleType type, double price, Driver driver, int capacity) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVeh_name(name);
        vehicle.setType(type);
        vehicle.setPrice(price);
        vehicle.setDriver(driver);
        vehicle.setCapacity(capacity);
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        return vehicle;
    }
}