package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.DTO.BookingRequest;
import com.projectSer.projectServices.enums.VehicleStatus;
import com.projectSer.projectServices.enums.DriverStatus;
import com.projectSer.projectServices.enums.BookingStatus;
import com.projectSer.projectServices.models.Booking;
import com.projectSer.projectServices.models.Driver;
import com.projectSer.projectServices.models.User;
import com.projectSer.projectServices.models.Vehicle;
import com.projectSer.projectServices.repositories.BookingRepository;
import com.projectSer.projectServices.repositories.UserRepository;
import com.projectSer.projectServices.repositories.VehicleRepository;
import com.projectSer.projectServices.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest bookingRequest) {
        // Fetch the user
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the vehicle
        Vehicle vehicle = vehicleRepository.findById(bookingRequest.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new RuntimeException("Vehicle is not available");
        }

        // Fetch the driver
        Driver driver = driverRepository.findById(bookingRequest.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        if (driver.getStatus() != DriverStatus.AVAILABLE) {
            throw new RuntimeException("Driver is not available");
        }

        // Update vehicle and driver status
        vehicle.setStatus(VehicleStatus.NOT_AVAILABLE);
        vehicleRepository.save(vehicle);

        driver.setStatus(DriverStatus.NOT_AVAILABLE);
        driverRepository.save(driver);

        // Create and populate the Booking object
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setDriver(driver);
        booking.setPickupLoc(bookingRequest.getPickupLoc());
        booking.setDestination(bookingRequest.getDestination());
        booking.setDate(bookingRequest.getDate());
        booking.setRentalTime(bookingRequest.getRentalTime());
        booking.setDistance(bookingRequest.getDistance());
        booking.setFinalPrice(bookingRequest.getFinalPrice());
        booking.setStatus(BookingStatus.PENDING);

        // Save the booking
        return bookingRepository.save(booking);
    }
}
