package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.enums.DriverStatus;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.repositories.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addDriver_shouldSaveAndReturnDriver() {
        // Arrange
        Driver mockDriver = new Driver();
        mockDriver.setName("John Doe");
        mockDriver.setAge(30);
        mockDriver.setPn("1234567890");
        mockDriver.setAddress("123 Street");
        mockDriver.setStatus(DriverStatus.AVAILABLE);

        when(driverRepository.save(any(Driver.class))).thenReturn(mockDriver);

        // Act
        Driver result = driverService.addDriver("John Doe", 30, "1234567890", "123 Street");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
        assertEquals(DriverStatus.AVAILABLE, result.getStatus());
        verify(driverRepository, times(1)).save(any(Driver.class));
    }

    @Test
    void getAllDrivers_shouldReturnListOfDrivers() {
        // Arrange
        List<Driver> mockDrivers = Arrays.asList(new Driver(), new Driver());
        when(driverRepository.findAll()).thenReturn(mockDrivers);

        // Act
        List<Driver> result = driverService.getAllDrivers();

        // Assert
        assertEquals(2, result.size());
        verify(driverRepository, times(1)).findAll();
    }
}
