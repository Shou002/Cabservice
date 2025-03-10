package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.DTO.BookingRequest;
import com.projectSer.projectServices.Services.BookingService;
import com.projectSer.projectServices.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            Booking newBooking = bookingService.createBooking(bookingRequest);
            return ResponseEntity.ok(newBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}