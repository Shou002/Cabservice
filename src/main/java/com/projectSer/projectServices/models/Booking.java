package com.projectSer.projectServices.models;

import com.projectSer.projectServices.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "veh_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private String pickupLoc;
    private String destination;
    private Date date;
    private int rentalTime;
    private double distance;
    private double finalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Transient
    private int vehicleId;

    @Transient
    private int driverId;
}
