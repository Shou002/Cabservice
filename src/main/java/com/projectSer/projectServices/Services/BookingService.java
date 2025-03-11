package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.DTO.BookingRequest;
import com.projectSer.projectServices.DTO.BookingResponse;
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
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<BookingResponse> getBookingsByUserId(int userId) {
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);

        return bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getBooId(),
                        booking.getVehicle().getVeh_name(),
                        booking.getDriver().getName(),
                        booking.getPickupLoc(),
                        booking.getDestination(),
                        booking.getDate(),
                        booking.getRentalTime(),
                        booking.getDistance(),
                        booking.getFinalPrice(),
                        booking.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse completeBooking(int booId){
        Booking booking = bookingRepository.findById(booId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if(booking.getStatus() == BookingStatus.COMPLETED){
            throw new RuntimeException("Booking is already completed");
        }

        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicle);

        Driver driver = booking.getDriver();
        driver.setStatus(DriverStatus.AVAILABLE);
        driverRepository.save(driver);

        return new BookingResponse(
                booking.getBooId(),
                vehicle.getVeh_name(),
                driver.getName(),
                booking.getPickupLoc(),
                booking.getDestination(),
                booking.getDate(),
                booking.getRentalTime(),
                booking.getDistance(),
                booking.getFinalPrice(),
                booking.getStatus()
        );
    }

    @Transactional
    public BookingResponse cancelBooking(int booId) {
        Booking booking = bookingRepository.findById(booId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed booking");
        }

        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);

        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicleRepository.save(vehicle);

        Driver driver = booking.getDriver();
        driver.setStatus(DriverStatus.AVAILABLE);
        driverRepository.save(driver);

        return new BookingResponse(
                booking.getBooId(),
                vehicle.getVeh_name(),
                driver.getName(),
                booking.getPickupLoc(),
                booking.getDestination(),
                booking.getDate(),
                booking.getRentalTime(),
                booking.getDistance(),
                booking.getFinalPrice(),
                booking.getStatus()
        );
    }


}
