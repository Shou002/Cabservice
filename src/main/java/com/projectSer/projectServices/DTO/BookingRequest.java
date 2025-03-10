package com.projectSer.projectServices.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class BookingRequest {
    private int userId;
    private int vehicleId;
    private int driverId;
    private String pickupLoc;
    private String destination;
    private Date date;
    private int rentalTime;
    private double distance;
    private double finalPrice;
}
