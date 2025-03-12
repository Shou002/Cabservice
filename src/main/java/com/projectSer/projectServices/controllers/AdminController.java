package com.projectSer.projectServices.controllers;


import com.projectSer.projectServices.models.*;
import com.projectSer.projectServices.Services.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final BookingService bookingService;

    public AdminController(UserService userService, DriverService driverService,
                           VehicleService vehicleService, BookingService bookingService) {
        this.userService = userService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.bookingService = bookingService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
}

