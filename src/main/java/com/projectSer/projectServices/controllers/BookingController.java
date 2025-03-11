package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.DTO.BookingRequest;
import com.projectSer.projectServices.DTO.BookingResponse;
import com.projectSer.projectServices.Services.BookingService;
import com.projectSer.projectServices.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(@PathVariable int userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @PutMapping("/complete/{booId}")
    public ResponseEntity<?> completeBooking(@PathVariable int booId){
        try{
            BookingResponse updateBooking = bookingService.completeBooking(booId);
            return ResponseEntity.ok(updateBooking);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{booId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int booId) {
        try {
            BookingResponse response = bookingService.cancelBooking(booId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}