package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.DTO.BookingResponse;
import com.projectSer.projectServices.enums.BookingStatus;
import com.projectSer.projectServices.enums.DriverStatus;
import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.models.Booking;
import com.projectSer.projectServices.models.Vehicle;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.repositories.BookingRepository;
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
import static org.mockito.Mockito.*;

class BookingServiceTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBookings_shouldReturnListOfBookings() {
        // Arrange
        List<Booking> mockBookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(mockBookings);

        // Act
        List<Booking> result = bookingService.getAllBookings();

        // Assert
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void completeBooking_shouldUpdateStatusAndReturnUpdatedBooking() {
        // Arrange
        Booking mockBooking = new Booking();
        mockBooking.setBooId(1);
        mockBooking.setStatus(BookingStatus.PENDING);

        // Mock vehicle and driver
        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setVehId(1);
        mockVehicle.setStatus(VehicleStatus.NOT_AVAILABLE);

        Driver mockDriver = new Driver();
        mockDriver.setDriverId(1);
        mockDriver.setStatus(DriverStatus.NOT_AVAILABLE);

        // Set relationships
        mockBooking.setVehicle(mockVehicle);
        mockBooking.setDriver(mockDriver);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(mockBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);

        // Act
        BookingResponse updatedBooking = bookingService.completeBooking(1);

        // Assert
        assertEquals(BookingStatus.COMPLETED, updatedBooking.getStatus());
        verify(bookingRepository, times(1)).save(mockBooking);
    }

}
